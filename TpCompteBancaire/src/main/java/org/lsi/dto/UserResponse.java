package org.lsi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.lsi.entities.Employe;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Data
public class UserResponse {

    private Long id;
    private String email;
    private String password;
    private String role;
    private Date createdAt;

    @JsonIgnore
    private Employe employe ;
}
