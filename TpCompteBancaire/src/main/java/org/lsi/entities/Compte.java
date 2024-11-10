package org.lsi.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonBackReference(value = "client-compte")
    @ManyToOne
    @JoinColumn(name="CODE_CLI")
    private Client client;

    @JsonBackReference(value = "employe-compte")
    @ManyToOne
    @JoinColumn(name="CODE_EMP")
    private Employe employe;

    @JsonManagedReference(value = "compte-operation")
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

    // Getters and setters
    public String getCodeCompte() {
        return codeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Collection<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Collection<Operation> operations) {
        this.operations = operations;
    }

//    public String getTypeCompte() {
//        return typeCompte;
//    }
//
//    public void setTypeCompte(String typeCompte) {
//        this.typeCompte = typeCompte;
//    }
}
