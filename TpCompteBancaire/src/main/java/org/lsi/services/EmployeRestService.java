package org.lsi.services;

import java.util.List;
import org.lsi.entities.Employe;
import org.lsi.metier.EmployeMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employes")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeRestService {

    @Autowired
    private EmployeMetier employeMetier;

    @PostMapping
    public Employe saveEmploye(@RequestBody Employe e) {
        return employeMetier.saveEmploye(e);
    }

    @GetMapping
    public List<Employe> listEmployes() {
        return employeMetier.listEmployes();
    }

    @GetMapping("/{codeEmploye}")
    public Employe getOneEmploye(@PathVariable Long codeEmploye){
        return employeMetier.getEmploye(codeEmploye);
    }

    @DeleteMapping("/{codeEmploye}")
    public Long deleteOneEmploye(@PathVariable Long codeEmploye) {
        return employeMetier.deleteEmploye(codeEmploye);
    }

    @PutMapping("/{codeEmploye}/groupes/{codeGroupe}")
    public void affecterEmployeGroupe(
            @PathVariable Long codeEmploye,
            @PathVariable Long codeGroupe
    ) {
        employeMetier.affecterEmployeGroupe(codeEmploye, codeGroupe);
    }
}