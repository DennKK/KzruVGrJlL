package dkcorp.caselab_file_service.service.file;

import dkcorp.caselab_file_service.dto.file.FileDto;
import dkcorp.caselab_file_service.dto.file.FileUploadDto;

public interface FileService {
    Long createFile(FileUploadDto fileUploadDto);

    FileDto findFile(Long fileId);
}
