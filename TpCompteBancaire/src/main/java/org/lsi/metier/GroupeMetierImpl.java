package org.lsi.metier;

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

@Service
@Transactional
public class GroupeMetierImpl implements GroupeMetier {

    @Autowired
    private GroupeRepository groupeRepository;

    @Override
    public GroupeResponse saveGroupe(GroupeRequest groupeRequest) {
        Groupe groupe = new Groupe();
        groupe.setNomGroupe(groupeRequest.getNomGroupe());

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
            dto.setEmployes((List<Employe>) groupe.getEmployes());
        }
        return dto;
    }


}