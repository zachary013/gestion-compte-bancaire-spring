package org.lsi.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OperationResponse {
    private Long codeOperation;
    private Date dateOperation;
    private double montant;
    private String type;  // "VERSEMENT" ou "RETRAIT"
    private String codeCompte;  // Source account
    private String codeCompteDest;  // Destination account
    private Double soldeApresOperation;
    private String nomEmploye;
}
