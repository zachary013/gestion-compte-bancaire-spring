package org.lsi.metier;

import java.util.ArrayList;
import java.util.List;

import org.lsi.dao.EmployeRepository;
import org.lsi.dao.GroupeRepository;
import org.lsi.entities.Employe;
import org.lsi.entities.Groupe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeMtierImpl implements EmployeMetier {

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private GroupeRepository groupeRepository;

    @Override
    public Employe saveEmploye(Employe e) {
        return employeRepository.save(e);
    }

    @Override
    public List<Employe> listEmployes() {
        return employeRepository.findAll();
    }

    @Override
    public Employe getEmploye(Long codeEmploye) {
        return employeRepository.findById(codeEmploye)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    @Transactional
    public void affecterEmployeGroupe(Long codeEmploye, Long codeGroupe) {
        Employe employe = employeRepository.findById(codeEmploye)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));
        Groupe groupe = groupeRepository.findById(codeGroupe)
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé"));

        // Check if the employe is already in the group to avoid duplicates
        if (!employe.getGroupes().contains(groupe)) {
            employe.getGroupes().add(groupe);
            employeRepository.save(employe);
        }
    }


    @Override
    public Long deleteEmploye(Long codeEmploye) {
        Employe employe = employeRepository.findById(codeEmploye)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employeRepository.delete(employe);
        return employe.getCodeEmploye();
    }
}
