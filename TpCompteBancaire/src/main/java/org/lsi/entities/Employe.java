package org.lsi.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class Employe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codeEmploye;
    private String nomEmploye;

    @ManyToOne
    @JoinColumn(name = "CODE_EMP_SUP")
    private Employe employeSup;

    @ManyToMany
    @JoinTable(name = "EMP_GR")
    private Collection<Groupe> groupes;

    public Employe() {}

    public Employe(String nomEmploye) {
        this.nomEmploye = nomEmploye;
    }

    // Getters and setters
    public Long getCodeEmploye() {
        return codeEmploye;
    }

    public void setCodeEmploye(Long codeEmploye) {
        this.codeEmploye = codeEmploye;
    }

    public String getNomEmploye() {
        return nomEmploye;
    }

    public void setNomEmploye(String nomEmploye) {
        this.nomEmploye = nomEmploye;
    }

    public Employe getEmployeSup() {
        return employeSup;
    }

    public void setEmployeSup(Employe employeSup) {
        this.employeSup = employeSup;
    }

    public Collection<Groupe> getGroupes() {
        return groupes;
    }

    public void setGroupes(Collection<Groupe> groupes) {
        this.groupes = groupes;
    }
}