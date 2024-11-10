package org.lsi.dto;

import lombok.Data;
import java.util.List;

@Data
public class EmployeResponse {
    private Long codeEmploye;
    private String nomEmploye;
    private Long codeEmployeSuperieur;
    private String nomEmployeSuperieur;
    private List<Long> codesGroupes;
}
