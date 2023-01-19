package com.basf.codingtest.chemicals.patent;

import com.basf.codingtest.chemicals.exceptions.ApiResponseError;
import com.basf.codingtest.chemicals.utils.FileUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@Tag(name = "Patent Controller")
public class PatentController {

    private static final Logger logger = LoggerFactory.getLogger(PatentController.class);

    @Value("${chemicals.patents.loaded}")
    private String patentsLoadedPath;

    private IPatentService patentService;

    public PatentController(IPatentService patentService) {
        this.patentService = patentService;
    }

    @Operation(
                description = "Process the document uploaded and stores in database information about the patent",
                tags = {},
                requestBody = @RequestBody(
                        description = "Input file with the content of the patent",
                        required = true,
                        content = {
                                @Content(mediaType = "multipart/form-data",
                                    schema =
                                        @Schema(implementation = MultipartFile.class))}
                )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatentDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseError.class))})})
    @PostMapping(value = "/api/v1/patents",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PatentDto> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        logger.info("POST -  Received file: " + multipartFile.getOriginalFilename());
        File inputFile = FileUtils.multipartToFile(multipartFile, patentsLoadedPath);
        PatentDto result = patentService.processDocumentAndSave(inputFile);
        FileUtils.moveFile(inputFile.getAbsolutePath(), patentsLoadedPath);
        return new ResponseEntity<PatentDto>(result, HttpStatus.OK);
    }

    @Operation(description = "Deletes the database content", tags = {})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")})
    @DeleteMapping(value = "/api/v1/patents")
    ResponseEntity<Void> deleteDatabase() {
        logger.info("DELETE -  Deleting database");
        this.patentService.deleteDatabase();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
