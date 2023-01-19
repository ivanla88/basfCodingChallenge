package com.basf.codingtest.chemicals.utils;

import com.basf.codingtest.chemicals.exceptions.IncorrectInputFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;

public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static File multipartToFile(MultipartFile input, String pathToFile) {

        File convFile = new File(pathToFile + FileSystems.getDefault().getSeparator() + input.getOriginalFilename());
        logger.info("File uploaded located in: " + convFile);
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(input.getBytes());
            fos.close();
        } catch (IOException e) {
            throw new IncorrectInputFileException("Error creating input file: " + e.getMessage());
        }
        return convFile;
    }

    public static boolean moveFile(String source, String destination) {
        File input = new File(source);
        String outputPath = destination + FileSystems.getDefault().getSeparator() + input.getName();
        File output = new File(outputPath);
        return input.renameTo(output);
    }
}
