package dkcorp.caselab_file_service.dto.file;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadDto {
    @NotBlank(message = "File data is required")
    private String fileData;

    @NotBlank(message = "Title is required. Example: FILE_TITLE")
    @Size(min = 1, max = 32, message = "Title should be between 1 and 32 characters. Example: FILE_TITLE")
    private String title;

    @Size(max = 500, message = "Description should not exceed 500 characters. Example: This is example of description.")
    private String description;
}
