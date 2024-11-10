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

    public GroupeResponse saveGroupe(GroupeRequest groupeRequest) {
        Groupe groupe = new Groupe();
        groupe.setNomGroupe(groupeRequest.getNomGroupe());

        // Gérer les employés par leurs IDs
        if (groupeRequest.getCodesEmployes() != null && !groupeRequest.getCodesEmployes().isEmpty()) {
            List<Employe> employes = new ArrayList<>();
            for (Long codeEmploye : groupeRequest.getCodesEmployes()) {
                Employe employe = employeRepository.findById(codeEmploye)
                        .orElseThrow(() -> new RuntimeException("Employé avec ID " + codeEmploye + " non trouvé"));
                employes.add(employe);
            }
            groupe.setEmployes(employes);
        }

        Groupe savedGroupe = groupeRepository.save(groupe);
        return convertToDTO(savedGroupe);
    }

    @Override
    public List<GroupeResponse> listGroupes() {
        List<Groupe> groupes = groupeRepository.findAll();
        List<GroupeResponse> groupeResponses = new ArrayList<>();
        for (Groupe groupe : groupes) {
            groupeResponses.add(convertToDTO(groupe));
        }
        return groupeResponses;
    }

    @Override
    public GroupeResponse getGroupe(Long id) {
        return convertToDTO(groupeRepository.findById(id).orElse(null));
    }

    @Override
    public GroupeResponse updateGroupe(Long id, GroupeRequest newGroupeData) {
        Groupe updatedGroupe = groupeRepository.findById(id).map(groupe -> {
            // Update the nomGroupe field with the new value
            groupe.setNomGroupe(newGroupeData.getNomGroupe());
            // Save the updated group
            return groupeRepository.save(groupe);
        }).orElse(null); // Return null if the group with the specified ID is not found
        return convertToDTO(updatedGroupe) ;
    }


    @Override
    public void deleteGroupe(Long id) {
        groupeRepository.deleteById(id);
    }


    private GroupeResponse convertToDTO(Groupe groupe) {
        GroupeResponse dto = new GroupeResponse();
        dto.setCodeGroupe(groupe.getCodeGroupe());
        dto.setNomGroupe(groupe.getNomGroupe());

        if (groupe.getEmployes() != null) {
            List<EmployeResponse> employeResponses = groupe.getEmployes().stream()
                    .map(employe -> {
                        EmployeResponse empDTO = new EmployeResponse();
                        empDTO.setCodeEmploye(employe.getCodeEmploye());
                        empDTO.setNomEmploye(employe.getNomEmploye());
                        // Setter d'autres champs si nécessaire
                        return empDTO;
                    })
                    .collect(Collectors.toList());
            dto.setEmployes(employeResponses);
        }
        return dto;
    }


}