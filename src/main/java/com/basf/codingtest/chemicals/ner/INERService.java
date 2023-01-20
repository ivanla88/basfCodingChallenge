package com.basf.codingtest.chemicals.ner;

import java.util.List;

public interface INERService {

    List<String> filterWords(String text, String type);

}
