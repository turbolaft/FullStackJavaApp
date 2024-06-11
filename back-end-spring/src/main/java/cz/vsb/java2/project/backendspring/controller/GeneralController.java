package cz.vsb.java2.project.backendspring.controller;

import cz.vsb.java2.project.backendspring.dto.UserDTO;
import cz.vsb.java2.project.backendspring.dto.UserRecordDTO;
import cz.vsb.java2.project.backendspring.entity.Map;
import cz.vsb.java2.project.backendspring.entity.Record;
import cz.vsb.java2.project.backendspring.entity.User;
import cz.vsb.java2.project.backendspring.repository.MapRepository;
import cz.vsb.java2.project.backendspring.repository.RecordRepository;
import cz.vsb.java2.project.backendspring.utils.MapImageRecreator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.catalina.manager.StatusTransformer.setContentType;

@RestController
@RequestMapping("/")
public class GeneralController {
    private RecordRepository recordRepository;
    private MapRepository mapRepository;

    @Autowired
    public void setRecordRepository(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Autowired
    public void setMapRepository(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    @GetMapping("/records")
    List<UserRecordDTO> getRecords() {
        List<UserRecordDTO> userRecordDTOs = new ArrayList<>();
        List<Record> records = recordRepository.findAll();

        for (Record record : records) {
            User user = record.getUser();
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(user.getUsername());
            userDTO.setEmail(user.getEmail());

            UserRecordDTO userRecordDTO = new UserRecordDTO();
            userRecordDTO.setUser(userDTO);
            userRecordDTO.setRecord(record);

            userRecordDTOs.add(userRecordDTO);
        }

        return userRecordDTOs;
    }

    @GetMapping("/records/{id}")
    public ResponseEntity<Record> getRecordById(@PathVariable Long id) {
        Optional<Record> recordOptional = recordRepository.findById(id);
        if (recordOptional.isPresent()) {
            return new ResponseEntity<>(recordOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
