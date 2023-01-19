package com.basf.codingtest.chemicals.patent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
public class PatentMapperTest {

    @Mock
    private ModelMapper mockMapper;

    @InjectMocks
    private PatentMapper parentMapper;

    private String fileName = "testFilePatent.xml";
    private String title = "Test file Patent Title";
    private String abstractContent = "Test file Patent Content";
    private Integer year = Integer.valueOf(2020);

    @Test
    public void testToDto() {
        Patent patent = Patent.builder()
                .fileName(fileName)
                .title(title)
                .abstractContent(abstractContent)
                .year(year)
                .build();

        Mockito.when(mockMapper.map(patent, PatentDto.class)).thenReturn((new ModelMapper()).map(patent, PatentDto.class));
        PatentDto dto = parentMapper.toDto(patent);

        assertAll("All fields should be the same",
                () -> dto.getTitle().equals(title),
                () -> dto.getFileName().equals(fileName),
                () -> dto.getYear().equals(year),
                () -> dto.getAbstractContent().equals(abstractContent)
        );
    }

    @Test
    public void testToModel() {
        PatentDto patentDto = PatentDto.builder()
                .fileName(fileName)
                .title(title)
                .abstractContent(abstractContent)
                .year(year)
                .build();

        Mockito.when(mockMapper.map(patentDto, Patent.class)).thenReturn((new ModelMapper()).map(patentDto, Patent.class));
        Patent model = parentMapper.toModel(patentDto);

        assertAll("All fields should be the same",
                () -> model.getTitle().equals(title),
                () -> model.getFileName().equals(fileName),
                () -> model.getYear().equals(year),
                () -> model.getAbstractContent().equals(abstractContent)
        );

    }
}