package dkcorp.caselab_test_task.mapper;

import dkcorp.caselab_test_task.dto.file.FileDto;
import dkcorp.caselab_test_task.dto.file.FileUploadDto;
import dkcorp.caselab_test_task.entity.FileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Base64;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface FileMapper {
    @Mapping(target = "fileData", expression = "java(mapBase64ToByteArray(fileUploadDto.getFile()))")
    FileEntity uploadDtoToEntity(FileUploadDto fileUploadDto);

    @Mapping(target = "file", expression = "java(mapByteArrayToBase64(fileEntity.getFileData()))")
    FileDto entityToFileDto(FileEntity fileEntity);

    default String mapByteArrayToBase64(byte[] fileData) {
        return Base64.getEncoder().encodeToString(fileData);
    }

    default byte[] mapBase64ToByteArray(String file) {
        return Base64.getDecoder().decode(file);
    }
}
