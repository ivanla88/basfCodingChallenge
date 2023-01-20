package com.basf.codingtest.chemicals.ner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
class INERServiceTest {

    @InjectMocks
    private NERService nerService;

    @Test
    public void givenAText_whenIndicatingType_thenReturnWordsFiltered() {

        String inputText = new String("This is a sentence used as example for the tokenizer");
        List<String> wordsFiltered = this.nerService.filterWords(inputText, "NOUN");
        assertTrue(wordsFiltered.containsAll(Arrays.asList("sentence", "example", "tokenizer")));

    }


}