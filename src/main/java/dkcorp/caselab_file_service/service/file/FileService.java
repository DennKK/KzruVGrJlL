package dkcorp.caselab_file_service.service.file;

import dkcorp.caselab_file_service.dto.file.FileDto;
import dkcorp.caselab_file_service.dto.file.FileUploadDto;
import org.springframework.data.domain.Page;

public interface FileService {
    Page<FileDto> findAllFiles(int page, int size, String sortBy);

    FileDto findFile(Long fileId);

    Long createFile(FileUploadDto fileUploadDto);
}
