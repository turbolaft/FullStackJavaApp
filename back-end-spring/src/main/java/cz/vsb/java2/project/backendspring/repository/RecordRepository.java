package cz.vsb.java2.project.backendspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import cz.vsb.java2.project.backendspring.entity.Record;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    @Modifying
    @Query("DELETE FROM Record r WHERE r.id = ?1")
    void deleteById(Long id);
}
