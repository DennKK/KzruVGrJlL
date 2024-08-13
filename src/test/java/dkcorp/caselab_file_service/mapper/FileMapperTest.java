package dkcorp.caselab_file_service.mapper;

import dkcorp.caselab_file_service.dto.file.FileDto;
import dkcorp.caselab_file_service.dto.file.FileUploadDto;
import dkcorp.caselab_file_service.entity.FileEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileMapperTest {
    private final FileMapper fileMapper = Mappers.getMapper(FileMapper.class);

    @Test
    void mapEntityToDto() {
        byte[] fileData = new byte[]{1, 2, 3};
        String title = "Название файла";
        String description = "Описание файла";

        FileEntity fileEntity = FileEntity.builder()
                .fileData(fileData)
                .title(title)
                .description(description)
                .build();

        FileDto fileDto = fileMapper.entityToDto(fileEntity);

        assertEquals(fileEntity.getTitle(), fileDto.getTitle());
        assertEquals(fileEntity.getDescription(), fileDto.getDescription());

        String expectedBase64 = fileMapper.mapByteArrayToBase64(fileEntity.getFileData());
        assertEquals(expectedBase64, fileDto.getFileData());
    }

    @Test
    void mapUploadDtoToEntity() {
        byte[] originalFileData = new byte[]{1, 2, 3};
        String base64FileData = Base64.getEncoder().encodeToString(originalFileData);

        FileUploadDto fileUploadDto = new FileUploadDto();
        fileUploadDto.setFileData(base64FileData);
        fileUploadDto.setTitle("Название файла");
        fileUploadDto.setDescription("Описание файла");

        FileEntity fileEntity = fileMapper.uploadDtoToEntity(fileUploadDto);

        assertArrayEquals(originalFileData, fileEntity.getFileData());
        assertEquals(fileUploadDto.getTitle(), fileEntity.getTitle());
        assertEquals(fileUploadDto.getDescription(), fileEntity.getDescription());
    }
}
