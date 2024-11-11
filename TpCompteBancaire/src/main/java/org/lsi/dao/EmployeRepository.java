package org.lsi.dao;

import org.lsi.entities.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeRepository extends JpaRepository<Employe, Long> {

    @Query("SELECT COUNT(e) FROM Employe e")
    Long countTotalEmployees(); // Custom query to count total employees

}

