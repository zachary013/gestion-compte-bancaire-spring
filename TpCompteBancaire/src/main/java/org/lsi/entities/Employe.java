package org.lsi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codeEmploye;

    @NotBlank(message = "Le nom de l'employé ne peut pas être vide")
    private String nomEmploye;

    @JsonBackReference(value = "employe-sup")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODE_EMP_SUP")
    private Employe employeSup;

    @JsonManagedReference(value = "employe-sub")
    @OneToMany(mappedBy = "employeSup", fetch = FetchType.LAZY)
    private Collection<Employe> subEmployes;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "EMP_GR",
            joinColumns = @JoinColumn(name = "CODE_EMP"),
            inverseJoinColumns = @JoinColumn(name = "CODE_GR"))
    private Collection<Groupe> groupes;

    @JsonManagedReference(value = "employe-compte")
    @OneToMany(mappedBy = "employe", fetch = FetchType.LAZY)
    private Collection<Compte> comptes;

    @JsonManagedReference(value = "employe-operation")
    @OneToMany(mappedBy = "employe", fetch = FetchType.LAZY)
    private Collection<Operation> operations;

    public Employe(String nomEmploye) {
        this.nomEmploye = nomEmploye;
    }
}