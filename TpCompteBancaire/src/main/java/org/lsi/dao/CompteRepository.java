package org.lsi.dao;

import org.lsi.entities.Client;
import org.lsi.entities.Compte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompteRepository extends JpaRepository<Compte, String> {
    Page<Compte> findByClient(Client client, Pageable pageable);
    @Query("SELECT COUNT(c) FROM Compte c")
    Long countTotalComptes(); // Get the total number of comptes

}
