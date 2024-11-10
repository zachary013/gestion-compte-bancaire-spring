package org.lsi.metier;

import java.util.List;

import org.lsi.dto.EmployeRequest;
import org.lsi.dto.EmployeResponse;
import org.lsi.entities.Employe;

public interface EmployeMetier {
    public EmployeResponse saveEmploye(EmployeRequest employeRequest);
    public EmployeResponse updateEmploye(Long codeEmploye, EmployeRequest employeRequest);
    public List<EmployeResponse> listEmployes();
    public EmployeResponse getEmploye(Long codeEmploye);
    public void deleteEmploye(Long codeEmploye);
}
