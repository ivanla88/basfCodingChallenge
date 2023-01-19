package com.basf.codingtest.chemicals.utils;

import com.basf.codingtest.chemicals.exceptions.IncorrectInputFileException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class FileUtilsTest {

    @AfterAll
    public static void deleteFile() {
        File fileOutput = new File("src/test/resources/inputFile.xml");
        fileOutput.delete();
    }

    @Test
    public void testConvertMultipartToFile() {

        MockMultipartFile inputFile = new MockMultipartFile("inputFile", "inputFile.xml", "text/plain", "Test for the conversion from a multipart file to a file in java".getBytes());
        File output = FileUtils.multipartToFile(inputFile, "src/test/resources");
        assertNotNull(output);
        assertEquals(output.getName(), "inputFile.xml");
    }

    @Test
    public void testConvertMultipartToFile_whenBadOutputFile_thenException() {

        MockMultipartFile inputFile = new MockMultipartFile("inputFile", "inputFile.xml", "text/plain", "Test for the conversion from a multipart file to a file in java".getBytes());
        assertThrows(IncorrectInputFileException.class, () -> {
            FileUtils.multipartToFile(inputFile, "src/test/resourcess");
        });
    }

    @Test
    public void testMoveFile_NotExisting() {
        assertFalse(FileUtils.moveFile("src/test/resources/inputFalse.txt", "src/test/resources"));
    }

    @Test
    public void testMoveFile_Existing() {
        assertTrue(FileUtils.moveFile("src/test/resources/input.txt", "src/test/resources"));
    }
}