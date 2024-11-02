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
    @Transactional
    public Employe affecterEmployeGroupe(Long codeEmploye, Long codeGroupe) {
        Employe employe = employeRepository.findById(codeEmploye)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));
        Groupe groupe = groupeRepository.findById(codeGroupe)
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé"));

        if (employe.getGroupes() == null) {
            employe.setGroupes(new ArrayList<>());
        }
        employe.getGroupes().add(groupe);
        return employeRepository.save(employe);
    }
}
