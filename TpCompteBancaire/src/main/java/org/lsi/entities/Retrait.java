package org.lsi.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Date;

@Entity
@DiscriminatorValue("R")
public class Retrait extends Operation {
    public Retrait() {
        super();
    }

    public Retrait(Date dateOperation, double montant) {
        super(dateOperation, montant);
    }
}