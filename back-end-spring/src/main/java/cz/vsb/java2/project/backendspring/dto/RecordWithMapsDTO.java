package cz.vsb.java2.project.backendspring.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecordWithMapsDTO {
    private RecordDTO recordDTO;
    private List<MapDTO> maps;
}
