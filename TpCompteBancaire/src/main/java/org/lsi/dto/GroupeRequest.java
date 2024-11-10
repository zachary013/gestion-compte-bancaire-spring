package org.lsi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class GroupeRequest {
    @NotNull(message = "Le nom du groupe ne peut pas être null")
    @NotBlank(message = "Le nom du groupe ne peut pas être vide")
    private String nomGroupe;
    private List<Long> codesEmployes;  // Format unifié: liste d'IDs
}