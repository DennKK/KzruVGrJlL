package dkcorp.caselab_file_service.controller;

import dkcorp.caselab_file_service.dto.file.FileDto;
import dkcorp.caselab_file_service.dto.file.FileUploadDto;
import dkcorp.caselab_file_service.exception.EntityNotFoundException;
import dkcorp.caselab_file_service.service.file.FileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileControllerTest {
    @InjectMocks
    private FileController fileController;

    @Mock
    private FileService fileService;

    @Test
    void getFiles_returnsPagedFileDtos() {
        int page = 0;
        int size = 2;
        String sortBy = "creationDate";

        List<FileDto> fileDtoList = Arrays.asList(
                FileDto.builder()
                        .fileData("VGhpcyBpcyB0aGUgZmlsZSBkZXNjcmlwdGlvbg==")
                        .title("File title 1")
                        .description("This is the first file description")
                        .creationDate(LocalDateTime.now())
                        .build(),
                FileDto.builder()
                        .fileData("VGhpcyBpcyB0aGUgZmlsZSBkZXNjcmlwdGlvbg==")
                        .title("File title 2")
                        .description("This is the second file description")
                        .creationDate(LocalDateTime.now().plusMinutes(10))
                        .build()
        );

        Page<FileDto> fileDtoPage = new PageImpl<>(fileDtoList);

        when(fileService.findAllFiles(page, size, sortBy)).thenReturn(fileDtoPage);

        ResponseEntity<Page<FileDto>> response = fileController.getFiles(page, size, sortBy);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fileDtoPage, response.getBody());
        verify(fileService).findAllFiles(page, size, sortBy);
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
