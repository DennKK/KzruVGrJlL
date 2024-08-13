package dkcorp.caselab_test_task.repository;

import dkcorp.caselab_test_task.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
