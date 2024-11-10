package org.lsi.services;

import org.lsi.dto.EmployeRequest;
import org.lsi.dto.EmployeResponse;
import org.lsi.metier.EmployeMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employes")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeRestService {

    @Autowired
    private EmployeMetier employeMetier;

    @PostMapping
    public EmployeResponse saveEmploye(@RequestBody EmployeRequest employeRequest) {
        return employeMetier.saveEmploye(employeRequest);
    }

    @PutMapping("/{codeEmploye}")
    public EmployeResponse updateEmploye(
            @PathVariable Long codeEmploye,
            @RequestBody EmployeRequest employeRequest) {
        return employeMetier.updateEmploye(codeEmploye, employeRequest);
    }

    @GetMapping
    public List<EmployeResponse> listEmployes() {
        return employeMetier.listEmployes();
    }

    @GetMapping("/{codeEmploye}")
    public EmployeResponse getEmploye(@PathVariable Long codeEmploye) {
        return employeMetier.getEmploye(codeEmploye);
    }

    @DeleteMapping("/{codeEmploye}")
    public void deleteEmploye(@PathVariable Long codeEmploye) {
        employeMetier.deleteEmploye(codeEmploye);
    }
}
