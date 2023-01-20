package com.basf.codingtest.chemicals.utils;

import com.basf.codingtest.chemicals.exceptions.IncorrectInputFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;

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
        try {
            Files.move(Path.of(source), Path.of(outputPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.error("Error moving file: " + e.getMessage(), e);
            return false;
        }
        return true;
    }
}
