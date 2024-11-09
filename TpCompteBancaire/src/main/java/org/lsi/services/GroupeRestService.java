package org.lsi.services;

import org.lsi.entities.Groupe;
import org.lsi.metier.GroupeMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groupes")
@CrossOrigin(origins = "http://localhost:4200")
public class GroupeRestService {

    @Autowired
    private GroupeMetier groupeMetier;

    @PostMapping
    public Groupe saveGroupe(@RequestBody Groupe groupe) {
        return groupeMetier.saveGroupe(groupe);
    }

    @GetMapping
    public List<Groupe> listGroupes() {
        return groupeMetier.listGroupes();
    }

    @GetMapping("/{id}")
    public Groupe getGroupe(@PathVariable Long id) {
        return groupeMetier.getGroupe(id);
    }

    @PutMapping("/{codeGroupe}")
    public Groupe updateGroupe(@PathVariable Long codeGroupe, @RequestBody Groupe groupe) {
        return groupeMetier.updateGroupe(codeGroupe, groupe);
    }

    @DeleteMapping("/{id}")
    public void deleteGroupe(@PathVariable Long id) {
        groupeMetier.deleteGroupe(id);
    }
}