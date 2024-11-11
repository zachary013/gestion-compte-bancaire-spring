package org.lsi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.lsi.entities.Client;
import org.lsi.entities.Employe;
import org.lsi.entities.Operation;

import java.util.Date;
import java.util.List;

@Data
public class CompteRequest {

    private String typeCompte;
    @NotNull(message = "code client ne peut pas etre vide")
    private Long codeClient ;
    @NotNull(message = "Le code employé ne peut pas être vide")
    private Long codeEmploye ;

    private Double decouvert ;
    private Double taux ;
    private Date dateCreation;
    private Double solde ;
    private List<Operation> operations ;
}
