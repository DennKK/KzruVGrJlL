package dkcorp.caselab_file_service.dto.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private String fileData;
    private String title;
    private LocalDateTime creationDate;
    private String description;
}
