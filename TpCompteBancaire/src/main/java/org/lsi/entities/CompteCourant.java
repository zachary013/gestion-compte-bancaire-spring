package org.lsi.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@DiscriminatorValue("CC")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class CompteCourant extends Compte {
    private Double decouvert;

    public CompteCourant() {}

    public CompteCourant(String codeCompte, Date dateCreation, double solde, Double decouvert) {
        super(codeCompte, dateCreation, solde);
        this.decouvert = decouvert;
    }
}
