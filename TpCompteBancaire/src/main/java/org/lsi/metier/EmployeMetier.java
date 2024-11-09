package org.lsi.metier;

import java.util.List;
import org.lsi.entities.Employe;

public interface EmployeMetier {
    public Employe saveEmploye(Employe e);
    public List<Employe> listEmployes();
    public Employe getEmploye(Long codeEmploye);
    public void affecterEmployeGroupe(Long codeEmploye, Long codeGroupe);
    public Long deleteEmploye(Long codeEmploye);
}
