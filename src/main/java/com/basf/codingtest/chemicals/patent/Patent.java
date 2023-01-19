package com.basf.codingtest.chemicals.patent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("patents")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Patent {

    private String fileName;
    private String abstractContent;
    private Integer year;
    private String title;
}
