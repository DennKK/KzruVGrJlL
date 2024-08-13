package dkcorp.caselab_file_service.controller;

import dkcorp.caselab_file_service.dto.file.FileDto;
import dkcorp.caselab_file_service.dto.file.FileUploadDto;
import dkcorp.caselab_file_service.exception.EntityNotFoundException;
import dkcorp.caselab_file_service.service.file.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FileControllerTest {
    @InjectMocks
    private FileController fileController;

    @Mock
    private FileService fileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void uploadFile_returnsId() {
        FileUploadDto fileUploadDto = FileUploadDto.builder()
                .fileData("VGhpcyBpcyB0aGUgZmlsZSBkZXNjcmlwdGlvbg==")
                .title("File title")
                .description("This is the file description")
                .build();
        Long expectedId = 1L;

        when(fileService.createFile(any(FileUploadDto.class))).thenReturn(expectedId);

        ResponseEntity<Long> response = fileController.uploadFile(fileUploadDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedId, response.getBody());
        verify(fileService).createFile(fileUploadDto);
    }

    @Test
    void getFile_returnsDto() {
        Long fileId = 1L;
        FileDto fileDto = FileDto.builder()
                .fileData("VGhpcyBpcyB0aGUgZmlsZSBkZXNjcmlwdGlvbg==")
                .title("File title")
                .description("This is the file description")
                .creationDate(LocalDateTime.now())
                .build();

        when(fileService.findFile(fileId)).thenReturn(fileDto);

        ResponseEntity<FileDto> response = fileController.getFile(fileId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fileDto, response.getBody());
        verify(fileService).findFile(fileId);
    }

    @Test
    void getFile_throwsException() {
        Long fileId = 1L;
        when(fileService.findFile(fileId)).thenThrow(new EntityNotFoundException("File not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> fileController.getFile(fileId));

        assertEquals("File not found", exception.getMessage());
        verify(fileService).findFile(fileId);
    }
}
