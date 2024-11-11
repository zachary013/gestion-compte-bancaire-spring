package org.lsi.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Collection;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE_CPTE",discriminatorType=DiscriminatorType.STRING,length=2)
@Data
@Getter
@Setter
public abstract class Compte implements Serializable {
    @Id
    private String codeCompte;
    private Date dateCreation;
    private double solde;

    @ManyToOne
    @JoinColumn(name="CODE_CLI")
    private Client client;

    @ManyToOne
    @JoinColumn(name="CODE_EMP")
    private Employe employe;

    @OneToMany(mappedBy="compte")
    private Collection<Operation> operations;


    public Compte() {
        super();
    }

    public Compte(String codeCompte, Date dateCreation, double solde) {
        this.codeCompte = codeCompte;
        this.dateCreation = dateCreation;
        this.solde = solde;
    }
}
