package cz.vsb.java2.project.backendspring.dto;

import lombok.Data;

@Data
public class SigninRequest {
    private String username;
    private String password;
}
