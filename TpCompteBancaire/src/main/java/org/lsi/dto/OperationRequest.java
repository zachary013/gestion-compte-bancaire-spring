package org.lsi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OperationRequest {
    @NotNull(message = "Le montant est requis")
    @Positive(message = "Le montant doit être positif")
    private double montant;

    @NotBlank(message = "Le code du compte est requis")
    private String codeCompte;

    @NotBlank(message = "Le code du compte destination est requis")
    private String codeCompteDest;  // Destination account

    @NotNull(message = "Le code de l'employé est requis")
    private Long codeEmploye;

    private String typeOperation;
}
