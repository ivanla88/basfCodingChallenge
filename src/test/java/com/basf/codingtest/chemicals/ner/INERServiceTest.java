package com.basf.codingtest.chemicals.ner;

import opennlp.tools.dictionary.Dictionary;
import opennlp.tools.util.StringList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class INERServiceTest {

    @Mock
    private Dictionary dictionary;

    @InjectMocks
    private NERService nerService;

    @Test
    public void givenAText_whenIndicatingType_thenReturnWordsFiltered() {

        Mockito.when(dictionary.contains(any(StringList.class))).thenReturn(true);
        String inputText = new String("This is a sentence used as example for the tokenizer");
        List<String> wordsFiltered = this.nerService.filterWords(inputText, "NOUN");
        assertTrue(wordsFiltered.containsAll(Arrays.asList("sentence", "example", "tokenizer")));

    }


}