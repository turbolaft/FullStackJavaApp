package cz.vsb.java2.project.backendspring.dto;

import cz.vsb.java2.project.backendspring.entity.Record;
import cz.vsb.java2.project.backendspring.entity.User;
import lombok.Data;

@Data
public class UserRecordDTO {
    private UserDTO user;
    private Record record;
}
