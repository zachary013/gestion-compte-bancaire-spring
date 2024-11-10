package org.lsi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.lsi.entities.Employe;

import java.util.List;

@Data
public class GroupeRequest {

    @NotNull(message = "Le nom de l'employé ne peut pas être null")
    @NotBlank(message = "Le nom de l'employé ne peut pas être vide")
    String nomGroupe ;
    List<Employe> employes ;
}
