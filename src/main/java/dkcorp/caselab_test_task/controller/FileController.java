package dkcorp.caselab_test_task.controller;

import dkcorp.caselab_test_task.dto.file.FileDto;
import dkcorp.caselab_test_task.dto.file.FileUploadDto;
import dkcorp.caselab_test_task.service.file.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Tag(name = "File API", description = "Operations related to files")
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    @Operation(summary = "Upload a new file", description = "Creates a new file and returns its ID.")
    public ResponseEntity<Long> uploadFile(@RequestBody @Valid FileUploadDto fileUploadDto) {
        Long fileId = fileService.createFile(fileUploadDto);
        return new ResponseEntity<>(fileId, HttpStatus.CREATED);
    }

    @GetMapping("/{fileId}")
    @Operation(summary = "Get file details", description = "Retrieves file details by its ID.")
    public ResponseEntity<FileDto> getFile(@PathVariable Long fileId) {
        FileDto fileDto = fileService.findFile(fileId);
        return new ResponseEntity<>(fileDto, HttpStatus.OK);
    }
}
