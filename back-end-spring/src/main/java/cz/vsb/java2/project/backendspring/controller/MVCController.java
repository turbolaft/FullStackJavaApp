package cz.vsb.java2.project.backendspring.controller;

import cz.vsb.java2.project.backendspring.entity.Map;
import cz.vsb.java2.project.backendspring.repository.MapRepository;
import cz.vsb.java2.project.backendspring.utils.MapImageRecreator;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Optional;

@RestController
public class MVCController {
    private MapRepository mapRepository;

    @Autowired
    public void setMapRepository(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    @GetMapping(value = "/maps/{id}/before", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getRecordMapById(@PathVariable Long id) throws Exception {
        Optional<Map> mapOptional = mapRepository.findById(id);

        if (mapOptional.isPresent()) {
            Map map = mapOptional.get();
            String mapBefore = map.getMapBefore();
            byte[] imageBytes = MapImageRecreator.generateMapImage(MapImageRecreator.stringToByte2DArray(mapBefore), 15, 15);

            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping(value = "/maps/{id}/after", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getRecordMapByIdAfter(@PathVariable Long id) throws Exception {
        Optional<Map> mapOptional = mapRepository.findById(id);

        if (mapOptional.isPresent()) {
            Map map = mapOptional.get();
            String mapAfter = map.getMapAfter();
            byte[] imageBytes = MapImageRecreator.generateMapImage(MapImageRecreator.stringToByte2DArray(mapAfter),
                    15, 15);

            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
