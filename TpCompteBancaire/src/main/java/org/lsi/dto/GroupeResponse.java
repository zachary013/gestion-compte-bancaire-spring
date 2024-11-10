package org.lsi.dto;


import lombok.Data;
import java.util.List;

@Data
public class GroupeResponse {
    private Long codeGroupe;
    private String nomGroupe;
    private List<EmployeResponse> employes; // Utiliser EmployeResponse au lieu de Employe
}