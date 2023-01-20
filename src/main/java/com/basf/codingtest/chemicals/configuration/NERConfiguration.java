package com.basf.codingtest.chemicals.configuration;

import com.basf.codingtest.chemicals.ner.NERService;
import opennlp.tools.dictionary.Dictionary;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.StringList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

@Configuration
public class NERConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(NERConfiguration.class);

    @Bean
    public Dictionary createDictionary() {

        InputStream is = this.getClass().getClassLoader().getResourceAsStream("ner_model/dictionary.bin");
        try {
            InputStreamReader streamReader = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(streamReader);
            Dictionary mNameDictionary = new Dictionary(false);
            String line = null;
            while ((line = reader.readLine()) != null) {
                StringList entry = new StringList(line);
                if (!mNameDictionary.contains(entry)) {
                    mNameDictionary.put(entry);
                }
            }
            reader.close();
            return mNameDictionary;
        }  catch (IOException e) {
            logger.error("Error creating dictionary: " + e.getMessage(), e);
        }
        return null;
    }
}
