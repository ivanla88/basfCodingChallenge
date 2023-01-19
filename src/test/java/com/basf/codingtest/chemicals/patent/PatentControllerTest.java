package com.basf.codingtest.chemicals.patent;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Order(1)
class PatentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void create_entry_in_database_for_input_file() throws Exception {

        InputStream is = new FileInputStream(new File("src/test/resources/testFile1.xml"));
        MockMultipartFile file = new MockMultipartFile("file", "testFile1.xml", "multipart/form-data", is);
        MvcResult result = this.mockMvc.perform(
                        MockMvcRequestBuilders.multipart(HttpMethod.POST, "/api/v1/patents")
                                .file(file)
                )
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();

        PatentDto patentInfo = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<PatentDto>() {});
        assertNotNull(patentInfo);
        assertEquals(patentInfo.getFileName(), "testFile1.xml");
        assertEquals(patentInfo.getYear(), 2019);
    }

    @Test
    public void delete_database_content() throws Exception {

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.delete(URI.create("/api/v1/patents")))
                .andExpect(status().isOk())
                .andReturn();

    }
}