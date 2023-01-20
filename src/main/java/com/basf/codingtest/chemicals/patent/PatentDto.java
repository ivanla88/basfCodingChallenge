package com.basf.codingtest.chemicals.patent;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name="PatentDto", description="Information about the patent stored in database")
public class PatentDto {

    @Schema(description="Name of the original file", example = "inputFile.xml", type = "String")
    private String fileName;

    @Schema(description="Content of the abstract tag", example = "Content of the abstract section included in the patent file", type = "String")
    private String abstractContent;

    @Schema(description="Year of the date when the document was produced", example = "2020", type = "Integer")
    private Integer year;

    @Schema(description="Title of the patent document", example = "Patent Name", type = "String")
    private String title;

    @Schema(description="List of entities identified")
    private List<String> entities;
}
