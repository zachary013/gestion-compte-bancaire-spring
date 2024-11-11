package org.lsi.dao;

import org.lsi.entities.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupeRepository extends JpaRepository<Groupe, Long> {
    @Query("SELECT COUNT(g) FROM Groupe g")
    Long countTotalGroups(); // Custom query to count total groups

}
