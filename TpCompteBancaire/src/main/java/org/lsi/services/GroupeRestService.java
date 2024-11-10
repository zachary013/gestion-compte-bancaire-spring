package org.lsi.services;

import org.lsi.dto.GroupeRequest;
import org.lsi.dto.GroupeResponse;
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
    public GroupeResponse saveGroupe(@RequestBody GroupeRequest groupeRequest) {
        return groupeMetier.saveGroupe(groupeRequest);
    }

    @GetMapping
    public List<GroupeResponse> listGroupes() {
        return groupeMetier.listGroupes();
    }

    @GetMapping("/{codeGroupe}")
    public GroupeResponse getGroupe(@PathVariable Long codeGroupe) {
        return groupeMetier.getGroupe(codeGroupe);
    }

    @PutMapping("/{codeGroupe}")
    public GroupeResponse updateGroupe(
            @PathVariable Long codeGroupe,
            @RequestBody GroupeRequest groupeRequest) {
        return groupeMetier.updateGroupe(codeGroupe, groupeRequest);
    }

    @DeleteMapping("/{codeGroupe}")
    public void deleteGroupe(@PathVariable Long codeGroupe) {
        groupeMetier.deleteGroupe(codeGroupe);
    }
}