package dkcorp.caselab_test_task.service.file;

import dkcorp.caselab_test_task.dto.file.FileDto;
import dkcorp.caselab_test_task.dto.file.FileUploadDto;
import dkcorp.caselab_test_task.entity.FileEntity;
import dkcorp.caselab_test_task.mapper.FileMapper;
import dkcorp.caselab_test_task.repository.FileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Override
    public Long createFile(FileUploadDto fileUploadDto) {
        FileEntity fileEntity = fileMapper.uploadDtoToEntity(fileUploadDto);
        fileEntity.setCreationDate(LocalDateTime.now());
        return fileRepository.save(fileEntity).getId();
    }

    @Override
    public FileDto findFile(Long fileId) {
        return fileMapper.entityToFileDto(findFileEntityById(fileId));
    }

    private FileEntity findFileEntityById(Long fileId) {
        return fileRepository.findById(fileId).orElseThrow(() -> new EntityNotFoundException(String.format("File with id=%d not found", fileId)));
    }
}
