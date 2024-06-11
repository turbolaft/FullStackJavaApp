package cz.vsb.java2.project.backendspring.repository;

import cz.vsb.java2.project.backendspring.entity.Map;
import cz.vsb.java2.project.backendspring.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MapRepository extends JpaRepository<Map, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Map m WHERE m.record.id = ?1")
    void deleteByRecordId(Long id);
}
