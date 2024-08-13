package dkcorp.caselab_file_service.dto.file;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadDto {
    @NotBlank(message = "File data is required. Example: VGhpcyBpcyB0aGUgZmlsZSBkZXNjcmlwdGlvbg==")
    private String fileData;

    @NotBlank(message = "Title is required. Example: File title.")
    @Size(min = 1, max = 32, message = "Title should be between 1 and 32 characters. Example: File title.")
    private String title;

    @Size(max = 500, message = "Description should not exceed 500 characters. Example: This is the file description.")
    private String description;
}
