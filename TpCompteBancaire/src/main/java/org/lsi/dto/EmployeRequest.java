package org.lsi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class EmployeRequest {
    @NotBlank(message = "Le nom de l'employé ne peut pas être vide")
    private String nomEmploye;
    private Long codeEmployeSuperieur;
    private List<Long> codesGroupes;
}
