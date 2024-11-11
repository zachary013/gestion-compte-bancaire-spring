package org.lsi.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(length = 1)
@Data
public abstract class Operation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numeroOperation;
    private Date dateOperation;
    private double montant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODE_CPTE")
    private Compte compte;  // Source account reference

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODE_CPTE_DEST")
    private Compte compteDestination;

    @ManyToOne
    @JoinColumn(name = "CODE_EMP")
    private Employe employe;

    public Operation() {}

    public Operation(Date dateOperation, double montant) {
        this.dateOperation = dateOperation;
        this.montant = montant;
    }
}
