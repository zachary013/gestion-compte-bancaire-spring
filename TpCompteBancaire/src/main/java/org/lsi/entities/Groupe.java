package org.lsi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Getter
@Setter
public class Groupe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codeGroupe;
    private String nomGroupe;

    @ManyToMany(mappedBy = "groupes", cascade = CascadeType.ALL)
    private Collection<Employe> employes;

    public Groupe() {}

    public Groupe(String nomGroupe) {
        this.nomGroupe = nomGroupe;
    }

}