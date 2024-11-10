package org.lsi.metier;

import org.lsi.dao.EmployeRepository;
import org.lsi.dao.GroupeRepository;
import org.lsi.dto.EmployeResponse;
import org.lsi.dto.GroupeRequest;
import org.lsi.dto.GroupeResponse;
import org.lsi.entities.Employe;
import org.lsi.entities.Groupe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroupeMetierImpl implements GroupeMetier {

    @Autowired
    private GroupeRepository groupeRepository;
    @Autowired
    private EmployeRepository employeRepository;

    @Override
    public GroupeResponse saveGroupe(GroupeRequest groupeRequest) {
        Groupe groupe = new Groupe();
        groupe.setNomGroupe(groupeRequest.getNomGroupe());

        if (groupeRequest.getCodesEmployes() != null && !groupeRequest.getCodesEmployes().isEmpty()) {
            List<Employe> employes = groupeRequest.getCodesEmployes().stream()
                    .map(codeEmploye -> employeRepository.findById(codeEmploye)
                            .orElseThrow(() -> new RuntimeException("Employé avec ID " + codeEmploye + " non trouvé")))
                    .collect(Collectors.toList());
            groupe.setEmployes(employes);
        }

        Groupe savedGroupe = groupeRepository.save(groupe);
        return convertToDTO(savedGroupe);
    }

    @Override
    public List<GroupeResponse> listGroupes() {
        List<Groupe> groupes = groupeRepository.findAll();
        return groupes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GroupeResponse getGroupe(Long id) {
        return groupeRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public GroupeResponse updateGroupe(Long codeGroupe, GroupeRequest groupeData) {
        return groupeRepository.findById(codeGroupe)
                .map(groupe -> {
                    groupe.setNomGroupe(groupeData.getNomGroupe());

                    if (groupeData.getCodesEmployes() != null) {
                        List<Employe> employes = groupeData.getCodesEmployes().stream()
                                .map(codeEmploye -> employeRepository.findById(codeEmploye)
                                        .orElseThrow(() -> new RuntimeException("Employé avec ID " + codeEmploye + " non trouvé")))
                                .collect(Collectors.toList());
                        groupe.setEmployes(employes);
                    }

                    return convertToDTO(groupeRepository.save(groupe));
                })
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé avec l'ID: " + codeGroupe));
    }


    @Override
    public void deleteGroupe(Long codeGroupe) {
        Groupe groupe = groupeRepository.findById(codeGroupe)
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé avec l'ID: " + codeGroupe));

        // Clear associations
        if (groupe.getEmployes() != null) {
            groupe.getEmployes().forEach(employe -> employe.getGroupes().remove(groupe));
            groupe.getEmployes().clear();
        }

        groupeRepository.deleteById(codeGroupe);
    }


    private GroupeResponse convertToDTO(Groupe groupe) {
        GroupeResponse dto = new GroupeResponse();
        dto.setCodeGroupe(groupe.getCodeGroupe());
        dto.setNomGroupe(groupe.getNomGroupe());

        // Vérifiez et convertissez les employés associés, si présents
        if (groupe.getEmployes() != null && !groupe.getEmployes().isEmpty()) {
            List<EmployeResponse> employeResponses = groupe.getEmployes().stream()
                    .map(employe -> {
                        EmployeResponse empDTO = new EmployeResponse();
                        empDTO.setCodeEmploye(employe.getCodeEmploye());
                        empDTO.setNomEmploye(employe.getNomEmploye());
                        // Ajoutez d'autres champs si nécessaire
                        return empDTO;
                    })
                    .collect(Collectors.toList());
            dto.setEmployes(employeResponses);
        } else {
            dto.setEmployes(new ArrayList<>()); // Si pas d'employés, initialisez avec une liste vide
        }
        return dto;
    }


}