package com.basf.codingtest.chemicals.patent;

import com.basf.codingtest.chemicals.common.Constants;
import com.basf.codingtest.chemicals.ner.INERService;
import com.basf.codingtest.chemicals.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
class PatentServiceTest {

    @Mock
    private PatentMapper patentMapper;

    @Mock
    private IPatentRepository patentRepository;

    @Mock
    private INERService inerService;

    @InjectMocks
    private PatentService patentService;

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void givenADocument_whenIsProcessedAndSaved_thenReturnInformationSaved() {

        File inputFile = new File("src/test/resources/testFile1.xml");
        Patent patent = Patent.builder()
                            .fileName("testFile1.xml")
                            .title("Test file 1 Title")
                            .abstractContent("Test file 1 Content")
                            .year(2019)
                            .entities(Arrays.asList("Test", "file", "Content"))
                        .build();

        Mockito.when(patentRepository.save(Mockito.any(Patent.class))).thenReturn(patent);
        Mockito.when(patentMapper.toDto(patent)).thenReturn(modelMapper.map(patent, PatentDto.class));
        Mockito.when(inerService.filterWords(anyString(), anyString())).thenReturn(Arrays.asList("Test", "file", "Content"));

        PatentDto dtoOutput = this.patentService.processDocumentAndSave(inputFile);

        assertEquals(dtoOutput.getTitle(), patent.getTitle());
        assertEquals(dtoOutput.getYear(), patent.getYear());
        assertEquals(dtoOutput.getAbstractContent(), patent.getAbstractContent());
        assertEquals(dtoOutput.getFileName(), patent.getFileName());
        assertTrue(dtoOutput.getEntities().containsAll(Arrays.asList("Test", "file", "Content")));
    }

    @Test
    public void givenAFolder_whenThereAreNoDocuments_thenReturnEmptyList() {

        ReflectionTestUtils.setField(patentService, "patentsInputPath", "src/test/resources/emptyFolder");
        List<PatentDto> dtoOutput = this.patentService.processDocumentAndSave();
        assertTrue(dtoOutput.isEmpty());
    }

    @Test
    public void givenAFolder_whenThereAreDocuments_thenReturnPatentsInfo() throws IOException {

        Patent patent = Patent.builder()
                .fileName("testFile1.xml")
                .title("Test file 1 Title")
                .abstractContent("Test file 1 Content")
                .year(2019)
                .build();

        Patent patent2 = Patent.builder()
                .fileName("testFile2.xml")
                .title("Test file 2 Title")
                .abstractContent("Test file 2 Content")
                .year(2020)
                .build();


        Mockito.when(patentRepository.save(Mockito.any(Patent.class))).thenAnswer(
                (Answer<Patent>) invocationOnMock -> {
                    if ("testFile1.xml".equals("fileName"))
                        return patent;
                    else if ("testFile2.xml".equals("fileName"))
                        return patent2;

                    return null;
                }
        );
        Mockito.when(patentMapper.toDto(patent)).thenReturn(modelMapper.map(patent, PatentDto.class));
        Mockito.when(patentMapper.toDto(patent2)).thenReturn(modelMapper.map(patent2, PatentDto.class));

        ReflectionTestUtils.setField(patentService, "patentsInputPath", "src/test/resources");
        ReflectionTestUtils.setField(patentService, "patentsLoadedPath", "src/test/resources/loaded");
        List<PatentDto> dtoOutput = this.patentService.processDocumentAndSave();
        assertEquals(dtoOutput.size(), 2);
        File directory = new File("src/test/resources/loaded/");
        FileUtils.moveFile("src/test/resources/loaded/testFile1.xml", "src/test/resources/");
        FileUtils.moveFile("src/test/resources/loaded/testFile2.xml", "src/test/resources/");
    }

    @Test
    public void givenTheDatabaseWithDocuments_whenExecutingDelete_thenDatabaseEmpty() {

        this.patentService.deleteDatabase();
        Mockito.verify(patentRepository).deleteAll();
    }

}