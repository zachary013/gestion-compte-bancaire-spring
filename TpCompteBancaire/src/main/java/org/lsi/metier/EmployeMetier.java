package org.lsi.metier;

import java.util.List;

import org.lsi.dto.EmployeRequest;
import org.lsi.dto.EmployeResponse;
import org.lsi.entities.Employe;

public interface EmployeMetier {
    public EmployeResponse saveEmploye(EmployeRequest employeRequest);
    public EmployeResponse updateEmploye(Long codeEmploye, EmployeRequest employeRequest);
    public EmployeResponse getEmploye(Long codeEmploye);
    public List<EmployeResponse> listEmployes();
    public List<EmployeResponse> getEmployesParGroupe(Long codeGroupe);
    public void deleteEmploye(Long codeEmploye);
}
