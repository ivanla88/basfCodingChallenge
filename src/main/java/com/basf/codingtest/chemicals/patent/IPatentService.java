package com.basf.codingtest.chemicals.patent;

import java.io.File;
import java.util.List;

public interface IPatentService {

    List<PatentDto> processDocumentAndSave();

    PatentDto processDocumentAndSave(File patentFile);

    void deleteDatabase();
}
