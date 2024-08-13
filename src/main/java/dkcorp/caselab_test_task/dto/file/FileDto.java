package dkcorp.caselab_test_task.dto.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private String file;
    private String title;
    private LocalDateTime creationDate;
    private String description;
}
