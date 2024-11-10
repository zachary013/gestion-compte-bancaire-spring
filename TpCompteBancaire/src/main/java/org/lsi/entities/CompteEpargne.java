package org.lsi.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@DiscriminatorValue("CE")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class CompteEpargne extends Compte {
    private Double taux;

    public CompteEpargne() {}

    public CompteEpargne(String codeCompte, Date dateCreation, double solde, Double taux) {
        super(codeCompte, dateCreation, solde);
        this.taux = taux;
    }
}
