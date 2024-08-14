package dkcorp.caselab_file_service.service.file;

import dkcorp.caselab_file_service.dto.file.FileDto;
import dkcorp.caselab_file_service.dto.file.FileUploadDto;
import dkcorp.caselab_file_service.entity.FileEntity;
import dkcorp.caselab_file_service.exception.EntityNotFoundException;
import dkcorp.caselab_file_service.mapper.FileMapper;
import dkcorp.caselab_file_service.repository.FileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FileServiceTest {
    @InjectMocks
    private FileServiceImpl fileService;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private FileMapper fileMapper;

    private LocalDateTime creationDate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        creationDate = LocalDateTime.now();
    }

    @Test
    void findAllFiles_returnsPagedFileDtos() {
        int page = 0;
        int size = 2;
        String sortBy = "creationDate";
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        List<FileEntity> fileEntities = Arrays.asList(
                FileEntity.builder()
                        .id(1L)
                        .fileData("VGhpcyBpcyB0aGUgZmlsZSBkZXNjcmlwdGlvbg==".getBytes())
                        .title("File title 1")
                        .description("This is the first file description")
                        .creationDate(creationDate)
                        .build(),
                FileEntity.builder()
                        .id(2L)
                        .fileData("VGhpcyBpcyB0aGUgZmlsZSBkZXNjcmlwdGlvbg==".getBytes())
                        .title("File title 2")
                        .description("This is the second file description")
                        .creationDate(creationDate.plusMinutes(10))
                        .build()
        );

        Page<FileEntity> fileEntityPage = new PageImpl<>(fileEntities, pageable, fileEntities.size());

        when(fileRepository.findAll(pageable)).thenReturn(fileEntityPage);
        when(fileMapper.entityToDto(any(FileEntity.class))).thenAnswer(invocation -> {
            FileEntity entity = invocation.getArgument(0);
            return FileDto.builder()
                    .fileData(Base64.getEncoder().encodeToString(entity.getFileData()))
                    .title(entity.getTitle())
                    .description(entity.getDescription())
                    .creationDate(entity.getCreationDate())
                    .build();
        });

        Page<FileDto> result = fileService.findAllFiles(page, size, sortBy);

        assertEquals(fileEntities.size(), result.getContent().size());
        assertEquals("File title 1", result.getContent().get(0).getTitle());
        assertEquals("File title 2", result.getContent().get(1).getTitle());

        verify(fileRepository).findAll(pageable);
        verify(fileMapper, times(fileEntities.size())).entityToDto(any(FileEntity.class));
    }

    @Test
    void createFile_returnsId() {
        Long expectedId = 1L;
        FileEntity fileEntity = FileEntity.builder()
                .id(expectedId)
                .fileData("VGhpcyBpcyB0aGUgZmlsZSBkZXNjcmlwdGlvbg==".getBytes())
                .title("File title")
                .description("This is the file description")
                .build();
        FileUploadDto fileUploadDto = FileUploadDto.builder()
                .fileData("VGhpcyBpcyB0aGUgZmlsZSBkZXNjcmlwdGlvbg==")
                .title("File title")
                .description("This is the file description")
                .build();

        when(fileMapper.uploadDtoToEntity(fileUploadDto)).thenReturn(fileEntity);
        when(fileRepository.save(fileEntity)).thenReturn(fileEntity);

        Long resultId = fileService.createFile(fileUploadDto);

        assertEquals(expectedId, resultId);
        verify(fileMapper).uploadDtoToEntity(fileUploadDto);
        verify(fileRepository).save(fileEntity);
    }

    @Test
    void findFile_returnsDto() {
        Long fileId = 1L;
        FileEntity fileEntity = FileEntity.builder()
                .id(fileId)
                .fileData("VGhpcyBpcyB0aGUgZmlsZSBkZXNjcmlwdGlvbg==".getBytes())
                .title("File title")
                .description("This is the file description")
                .creationDate(creationDate)
                .build();
        FileDto expectedDto = FileDto.builder()
                .fileData("VGhpcyBpcyB0aGUgZmlsZSBkZXNjcmlwdGlvbg==")
                .title("File title")
                .description("This is the file description")
                .creationDate(creationDate)
                .build();

        when(fileRepository.findById(fileId)).thenReturn(Optional.of(fileEntity));
        when(fileMapper.entityToDto(fileEntity)).thenReturn(expectedDto);

        FileDto result = fileService.findFile(fileId);

        assertEquals(expectedDto, result);
        verify(fileRepository).findById(fileId);
        verify(fileMapper).entityToDto(fileEntity);
    }

    @Test
    void findFile_throwsException() {
        Long fileId = 1L;
        when(fileRepository.findById(fileId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> fileService.findFile(fileId));

        assertEquals(String.format("File with id=%d not found", fileId), exception.getMessage());
        verify(fileRepository).findById(fileId);
    }
}
