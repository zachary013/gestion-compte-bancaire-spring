package org.lsi.dto;

import lombok.Data;
import org.lsi.entities.Operation;

import java.util.Date;
import java.util.List;

@Data
public class CompteResponse {
    private String typeCompte ;
    private String codeCompte ;
    private Date dateCreation;
    private Double solde ;
    private List<Operation> operations ;
    private Double decouvert ;
    private Double taux ;
    private Long codeClient;
    private Long codeEmploye;

}
