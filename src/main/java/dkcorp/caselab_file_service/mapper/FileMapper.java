package dkcorp.caselab_file_service.mapper;

import dkcorp.caselab_file_service.dto.file.FileDto;
import dkcorp.caselab_file_service.dto.file.FileUploadDto;
import dkcorp.caselab_file_service.entity.FileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Base64;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface FileMapper {
    @Mapping(target = "fileData", expression = "java(mapBase64ToByteArray(fileUploadDto.getFileData()))")
    FileEntity uploadDtoToEntity(FileUploadDto fileUploadDto);

    @Mapping(target = "fileData", expression = "java(mapByteArrayToBase64(fileEntity.getFileData()))")
    FileDto entityToDto(FileEntity fileEntity);

    default String mapByteArrayToBase64(byte[] fileData) {
        return Base64.getEncoder().encodeToString(fileData);
    }

    default byte[] mapBase64ToByteArray(String fileData) {
        return Base64.getDecoder().decode(fileData);
    }
}
