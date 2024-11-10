package org.lsi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.lsi.entities.Employe;

import java.util.List;

@Data
public class GroupeResponse {
    private Long codeGroupe;
    private String nomGroupe;
    private List<EmployeResponse> employes; // Utiliser EmployeResponse au lieu de Employe
}