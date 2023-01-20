package com.basf.codingtest.chemicals.patent;

import com.basf.codingtest.chemicals.common.Constants;
import com.basf.codingtest.chemicals.exceptions.IncorrectInputFileException;
import com.basf.codingtest.chemicals.ner.INERService;
import com.basf.codingtest.chemicals.utils.DateUtils;
import com.basf.codingtest.chemicals.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatentService implements IPatentService{

    private static final Logger logger = LoggerFactory.getLogger(PatentService.class);

    @Value("${chemicals.patents.source}")
    private String patentsInputPath;

    @Value("${chemicals.patents.loaded}")
    private String patentsLoadedPath;

    private final IPatentRepository patentRepository;

    private final INERService inerService;

    private final PatentMapper mapper;

    public PatentService(IPatentRepository patentRepository, INERService inerService, PatentMapper mapper) {
        this.patentRepository = patentRepository;
        this.inerService = inerService;
        this.mapper = mapper;
    }

    @Override
    @Scheduled(cron = "0 */5 * ? * *")
    public List<PatentDto> processDocumentAndSave() {

        logger.info("Checking available patent files");
        File inputFolder = new File(patentsInputPath);
        return Arrays.stream(inputFolder.listFiles()).filter(f -> f.getName().endsWith(".xml")).parallel().map(
                file -> {
                    PatentDto output = this.processDocumentAndSave(file);
                    FileUtils.moveFile(file.getAbsolutePath(), patentsLoadedPath);
                    return output;
                })
                .collect(Collectors.toList());
    }

    @Override
    public PatentDto processDocumentAndSave(File patentFile) {

        String abstractContent = null;
        String titleContent = null;
        String yearContent = null;
        try {
            logger.info("Processing file: " + patentFile);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(patentFile);
            doc.getDocumentElement().normalize();
            abstractContent = this.extractNodeContent(doc, "abstract", "p");
            titleContent = this.extractNodeContent(doc, "bibliographic-data", "invention-title");
            yearContent = this.extractAttribute(doc, "questel-patent-document", "date-produced");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new IncorrectInputFileException("Error reading content file", e);
        }

        List<String> entities = inerService.filterWords(abstractContent, Constants.TYPE_ENTITIES_TO_IDENTIFY);

        Patent newElement = Patent.builder()
                .fileName(patentFile.getName())
                .abstractContent(abstractContent)
                .title(titleContent)
                .year(DateUtils.parseStringDate(yearContent, "yyyyMMdd").getYear())
                .entities(entities)
                .build();

        newElement = patentRepository.save(newElement);
        return mapper.toDto(newElement);
    }

    @Override
    public void deleteDatabase() {
        patentRepository.deleteAll();
    }

    private String extractNodeContent(Document doc, String rootNode, String childNode) {

        StringBuilder contentBuilder = new StringBuilder();
        NodeList nodeList = doc.getElementsByTagName(rootNode);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element node = (Element)nodeList.item(i);
            contentBuilder.append(node.getElementsByTagName(childNode).item(0).getTextContent());
        }
        return contentBuilder.toString();
    }

    private String extractAttribute(Document doc, String node, String attribute) {

        StringBuilder contentBuilder = new StringBuilder();
        NodeList nodeList = doc.getElementsByTagName(node);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element nodeElement = (Element)nodeList.item(i);
            contentBuilder.append(nodeElement.getAttribute(attribute));
        }
        return contentBuilder.toString();
    }
}
