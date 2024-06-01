package cz.vsb.java2.project.backendspring.controller;


import cz.vsb.java2.project.backendspring.dto.MapDTO;
import cz.vsb.java2.project.backendspring.dto.RecordDTO;
import cz.vsb.java2.project.backendspring.dto.RecordWithMapsDTO;
import cz.vsb.java2.project.backendspring.entity.Map;
import cz.vsb.java2.project.backendspring.entity.Record;
import cz.vsb.java2.project.backendspring.entity.User;
import cz.vsb.java2.project.backendspring.repository.MapRepository;
import cz.vsb.java2.project.backendspring.repository.RecordRepository;
import cz.vsb.java2.project.backendspring.repository.UserRepository;
import cz.vsb.java2.project.backendspring.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/secured")
public class MainController {
    private UserRepository userRepository;
    private RecordRepository recordRepository;
    private MapRepository mapRepository;

    private RecordService recordService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRecordRepository(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Autowired
    public void setMapRepository(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    @Autowired
    public void setRecordService(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/user")
    public String userAccess(Principal principal) {
        if (principal == null) {
            return null;
        }

        String name = principal.getName();

        User user = userRepository.findUserByUsername(name).get();

        return user.toString();
    }

    @PostMapping("/createRecord")
    public ResponseEntity<Record> createRecord(Principal principal, @RequestBody RecordWithMapsDTO recordWithMapsDTO) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(
                recordService.createRecordWithMaps(recordWithMapsDTO.getRecordDTO(), recordWithMapsDTO.getMaps(), principal.getName())
        );
    }

    @DeleteMapping("/deleteRecord/{id}")
    public ResponseEntity<?> deleteRecord(Principal principal, @PathVariable Long id) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }

        return recordService.deleteRecord(
                userRepository.findUserByUsername(principal.getName()).orElseThrow(() -> {
                    throw new RuntimeException("User not found");
                }), id);
    }

    @GetMapping("/records")
    public ResponseEntity<List<Record>> getRecords(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }

        String name = principal.getName();

        User user = userRepository.findUserByUsername(name).orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(user.getRecords());
    }

}
