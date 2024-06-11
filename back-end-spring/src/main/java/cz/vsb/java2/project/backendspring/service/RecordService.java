package cz.vsb.java2.project.backendspring.service;

import cz.vsb.java2.project.backendspring.dto.MapDTO;
import cz.vsb.java2.project.backendspring.dto.RecordDTO;
import cz.vsb.java2.project.backendspring.entity.Map;
import cz.vsb.java2.project.backendspring.entity.Record;
import cz.vsb.java2.project.backendspring.entity.User;
import cz.vsb.java2.project.backendspring.repository.MapRepository;
import cz.vsb.java2.project.backendspring.repository.RecordRepository;
import cz.vsb.java2.project.backendspring.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecordService {
    private RecordRepository recordRepository;
    private MapRepository mapService;
    private UserRepository userRepository;

    @Autowired
    public void setRecordRepository(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Autowired
    public void setMapService(MapRepository mapService) {
        this.mapService = mapService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Record createRecordWithMaps(RecordDTO recorddto, List<MapDTO> maps, String username) {
        Record record = new Record();
        record.setUser(userRepository.findUserByUsername(username).orElseThrow(() -> new RuntimeException("User not found")));
        record.setScore(recorddto.getScore());
        record.setPointWeight(recorddto.getPointWeight());
        Record savedRecord = recordRepository.save(record);

        for (MapDTO mapDTO : maps) {
            Map savedMap = new Map(mapDTO.getMapLevel(), mapDTO.getMapBefore(), mapDTO.getMapAfter());

            savedMap.setRecord(savedRecord);
            mapService.save(savedMap);
        }

        return savedRecord;
    }

    @Transactional
    public ResponseEntity<String> deleteRecord(User user, Long id) {
        Optional<Record> recordOpt = recordRepository.findById(id);

        if (recordOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Record not found");
        }

        if (!recordOpt.get().getUser().equals(user)) {
            return ResponseEntity.badRequest().body("Record does not belong to user");
        }

        mapService.deleteByRecordId(id);

        recordRepository.deleteById(id);

        return ResponseEntity.ok("Record deleted");
    }
}
