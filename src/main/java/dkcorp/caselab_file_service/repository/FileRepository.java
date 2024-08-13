package dkcorp.caselab_file_service.repository;

import dkcorp.caselab_file_service.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
