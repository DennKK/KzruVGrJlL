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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
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
    void createFile_ShouldReturnCreatedFileId() {
        FileUploadDto fileUploadDto = new FileUploadDto("VGhpcyBpcyB0aGUgZmlsZSBkZXNjcmlwdGlvbg==", "File title", "This is the file description");
        Long expectedId = 1L;

        when(fileService.createFile(any(FileUploadDto.class))).thenReturn(expectedId);

        ResponseEntity<Long> response = fileController.uploadFile(fileUploadDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedId, response.getBody());
        verify(fileService, times(1)).createFile(fileUploadDto);
    }

    @Test
    void findFile_ShouldReturnFileDto() {
        Long fileId = 1L;
        FileDto fileDto = new FileDto("fileData", "File title", null, "This is the file description");

        when(fileService.findFile(fileId)).thenReturn(fileDto);

        ResponseEntity<FileDto> response = fileController.getFile(fileId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fileDto, response.getBody());
        verify(fileService, times(1)).findFile(fileId);
    }

    @Test
    void findFile_FileNotFound_ShouldThrowException() {
        Long fileId = 1L;
        when(fileService.findFile(fileId)).thenThrow(new EntityNotFoundException("File not found"));

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            fileController.getFile(fileId);
        });

        assertEquals("File not found", exception.getMessage());
        verify(fileService, times(1)).findFile(fileId);
    }
}
