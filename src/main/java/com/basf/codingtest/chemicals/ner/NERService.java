package com.basf.codingtest.chemicals.ner;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NERService implements INERService{

    private static final Logger logger = LoggerFactory.getLogger(NERService.class);

    @Override
    public List<String> filterWords(String text, String type) {

        String[] tokensFromText = this.getTokensFromText(text);
        if (tokensFromText == null) {
            return null;
        }

        Map<String, String> tagsForWords = this.categorizedTokens(tokensFromText);
        if (tagsForWords == null) {
            return null;
        }

        List<String> wordsFiltered = tagsForWords.entrySet().stream()
                .filter(entry -> entry.getValue().equals(type))
                .map(e -> e.getKey())
                .collect(Collectors.toList());


        return wordsFiltered;
    }

    private String[] getTokensFromText(String text) {

        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("ner_model/en-token.bin");
            TokenizerModel model = new TokenizerModel(inputStream);
            TokenizerME tokenizer = new TokenizerME(model);
            return tokenizer.tokenize(text);
        } catch (IOException e) {
            logger.error("Error reading model file for tokens");
        }
        return null;
    }

    private Map<String, String> categorizedTokens(String[] tokens) {

        Map<String, String> tagsForWords = new HashMap<>();
        try {
            InputStream inputStreamPOSTagger = this.getClass().getClassLoader().getResourceAsStream("ner_model/en-pos.bin");
            POSModel posModel = new POSModel(inputStreamPOSTagger);
            POSTaggerME posTagger = new POSTaggerME(posModel);
            String[] tag = posTagger.tag(tokens);

            for (int i = 0; i < tokens.length; i++) {
                tagsForWords.put(tokens[i], tag[i]);
            }
            return tagsForWords;
        } catch (IOException e) {
            logger.error("Error reading model file for tags");
        }
        return null;
    }


}
