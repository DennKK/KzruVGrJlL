package dkcorp.caselab_file_service.service.file;

import dkcorp.caselab_file_service.dto.file.FileDto;
import dkcorp.caselab_file_service.dto.file.FileUploadDto;
import dkcorp.caselab_file_service.entity.FileEntity;
import dkcorp.caselab_file_service.exception.EntityNotFoundException;
import dkcorp.caselab_file_service.mapper.FileMapper;
import dkcorp.caselab_file_service.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Override
    public Page<FileDto> findAllFiles(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<FileEntity> fileEntities = fileRepository.findAll(pageable);
        return fileEntities.map(fileMapper::entityToDto);
    }

    @Override
    public FileDto findFile(Long fileId) {
        return fileMapper.entityToDto(findFileEntityById(fileId));
    }

    @Override
    public Long createFile(FileUploadDto fileUploadDto) {
        FileEntity fileEntity = fileMapper.uploadDtoToEntity(fileUploadDto);
        return fileRepository.save(fileEntity).getId();
    }

    private FileEntity findFileEntityById(Long fileId) {
        return fileRepository.findById(fileId).orElseThrow(() -> new EntityNotFoundException(String.format("File with id=%d not found", fileId)));
    }
}
