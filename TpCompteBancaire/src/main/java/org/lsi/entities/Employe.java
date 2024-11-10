package org.lsi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Employe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codeEmploye;

    @NotBlank(message = "Le nom de l'employé ne peut pas être vide")
    @NonNull
    private String nomEmploye;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODE_EMP_SUP")
    private Employe employeSuperieur;

    @OneToMany(mappedBy = "employeSuperieur", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Employe> employesSubordonnes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "EMP_GR",
            joinColumns = @JoinColumn(name = "CODE_EMP"),
            inverseJoinColumns = @JoinColumn(name = "CODE_GR"))
    private Collection<Groupe> groupes;

    @OneToMany(mappedBy = "employe", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Operation> operations;
}
