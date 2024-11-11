package org.lsi.metier;

import org.lsi.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardMetierImpl implements DashboardMetier{

    @Autowired
    private ClientRepository clientRepository ;

    @Autowired
    private CompteRepository compteRepository ;

    @Autowired
    private EmployeRepository employeRepository ;


    @Autowired
    private OperationRepository operationRepository ;

    @Autowired
    private GroupeRepository groupeRepository ;





    @Override
    public Map<String, Long> getQuantities() {
        Map<String, Long> quantities = new HashMap<String, Long>();
        quantities.put("Client", clientRepository.count());
        quantities.put("Compte", compteRepository.count());
        quantities.put("Employe", employeRepository.count());
        quantities.put("Operation", operationRepository.count());
        quantities.put("Groupe", groupeRepository.count());
        return quantities;
    }
}
