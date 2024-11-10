package org.lsi.metier;

import org.lsi.dao.EmployeRepository;
import org.lsi.dao.GroupeRepository;
import org.lsi.dto.EmployeRequest;
import org.lsi.dto.EmployeResponse;
import org.lsi.entities.Employe;
import org.lsi.entities.Groupe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeMtierImpl implements EmployeMetier {

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private GroupeRepository groupeRepository;

    @Override
    @Transactional
    public EmployeResponse saveEmploye(EmployeRequest employeRequest) {
        Employe employe = new Employe();
        employe.setNomEmploye(employeRequest.getNomEmploye());

        if (employeRequest.getCodeEmployeSuperieur() != null) {
            Employe employeSuperieur = employeRepository.findById(employeRequest.getCodeEmployeSuperieur())
                    .orElseThrow(() -> new RuntimeException("Employé supérieur non trouvé"));
            employe.setEmployeSuperieur(employeSuperieur);
        }

        Employe savedEmploye = employeRepository.save(employe);
        return convertToDTO(savedEmploye);
    }

    @Override
    @Transactional
    public EmployeResponse updateEmploye(Long codeEmploye, EmployeRequest employeRequest) {
        Employe employe = employeRepository.findById(codeEmploye)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));
        employe.setNomEmploye(employeRequest.getNomEmploye());

        if (employeRequest.getCodeEmployeSuperieur() != null) {
            Employe employeSuperieur = employeRepository.findById(employeRequest.getCodeEmployeSuperieur())
                    .orElseThrow(() -> new RuntimeException("Employé supérieur non trouvé"));
            employe.setEmployeSuperieur(employeSuperieur);
        } else {
            employe.setEmployeSuperieur(null);
        }

        List<Groupe> groupes = new ArrayList<>();
        if (employeRequest.getCodesGroupes() != null) {
            for (Long codeGroupe : employeRequest.getCodesGroupes()) {
                Groupe groupe = groupeRepository.findById(codeGroupe)
                        .orElseThrow(() -> new RuntimeException("Groupe non trouvé"));
                groupes.add(groupe);
            }
        }
        employe.setGroupes(groupes);

        Employe updatedEmploye = employeRepository.save(employe);
        return convertToDTO(updatedEmploye);
    }

    @Override
    public List<EmployeResponse> listEmployes() {
        List<Employe> employes = employeRepository.findAll();
        List<EmployeResponse> employeResponseList = new ArrayList<>();
        for (Employe employe : employes) {
            employeResponseList.add(convertToDTO(employe));
        }
        return employeResponseList;
    }

    @Override
    public EmployeResponse getEmploye(Long codeEmploye) {
        Employe employe = employeRepository.findById(codeEmploye)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));
        return convertToDTO(employe);
    }

    @Override
    @Transactional
    public void deleteEmploye(Long codeEmploye) {
        Employe employe = employeRepository.findById(codeEmploye)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));
        employeRepository.delete(employe);
    }

    private EmployeResponse convertToDTO(Employe employe) {
        EmployeResponse dto = new EmployeResponse();
        dto.setCodeEmploye(employe.getCodeEmploye());
        dto.setNomEmploye(employe.getNomEmploye());
        if (employe.getEmployeSuperieur() != null) {
            dto.setCodeEmployeSuperieur(employe.getEmployeSuperieur().getCodeEmploye());
            dto.setNomEmployeSuperieur(employe.getEmployeSuperieur().getNomEmploye());
        }
        if (employe.getGroupes() != null) {
            List<Long> codesGroupes = new ArrayList<>();
            employe.getGroupes().forEach(groupe -> codesGroupes.add(groupe.getCodeGroupe()));
            dto.setCodesGroupes(codesGroupes);
        }
        return dto;
    }
}
