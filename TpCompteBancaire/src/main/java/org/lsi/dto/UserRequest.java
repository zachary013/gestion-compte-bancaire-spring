package org.lsi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class UserRequest {
    @NotBlank(message = "email ne peut pas etre vide")
    private String email;
    @NotBlank(message = "password ne peut pas etre vide")
    private String password;
    private String role ;
    private Long codeEmploye ;
}
