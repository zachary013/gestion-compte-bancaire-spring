package org.lsi.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OperationResponse {
    private Long codeOperation;
    private Date dateOperation;
    private double montant;
    private String type;  // "VERSEMENT" ou "RETRAIT"
    private String codeCompte;
    private String codeCompte1;  // Source account
    private String codeCompte2;  // Destination account
    private Double soldeApresOperation;
    private String nomEmploye;
    private String prenomEmploye;
}
