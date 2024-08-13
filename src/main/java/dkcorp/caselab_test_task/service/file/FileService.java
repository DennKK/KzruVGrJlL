package dkcorp.caselab_test_task.service.file;

import dkcorp.caselab_test_task.dto.file.FileDto;
import dkcorp.caselab_test_task.dto.file.FileUploadDto;

public interface FileService {
    Long createFile(FileUploadDto fileUploadDto);

    FileDto findFile(Long fileId);
}
