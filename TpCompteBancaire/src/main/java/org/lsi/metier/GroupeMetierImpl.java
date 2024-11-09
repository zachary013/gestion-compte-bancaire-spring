package org.lsi.metier;

import org.lsi.dao.GroupeRepository;
import org.lsi.entities.Groupe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GroupeMetierImpl implements GroupeMetier {

    @Autowired
    private GroupeRepository groupeRepository;

    @Override
    public Groupe saveGroupe(Groupe groupe) {
        return groupeRepository.save(groupe);
    }

    @Override
    public List<Groupe> listGroupes() {
        return groupeRepository.findAll();
    }

    @Override
    public Groupe getGroupe(Long id) {
        return groupeRepository.findById(id).orElse(null);
    }

    @Override
    public Groupe updateGroupe(Long id, Groupe newGroupeData) {
        return groupeRepository.findById(id).map(groupe -> {
            // Update the nomGroupe field with the new value
            groupe.setNomGroupe(newGroupeData.getNomGroupe());
            // Save the updated group
            return groupeRepository.save(groupe);
        }).orElse(null); // Return null if the group with the specified ID is not found
    }


    @Override
    public void deleteGroupe(Long id) {
        groupeRepository.deleteById(id);
    }
}