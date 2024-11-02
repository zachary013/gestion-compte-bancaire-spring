package org.lsi.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("V")
public class Versement extends Operation {

    public Versement() {
        super();
    }

    public Versement(Date dateOperation, double montant) {
        super(dateOperation, montant);
    }
}