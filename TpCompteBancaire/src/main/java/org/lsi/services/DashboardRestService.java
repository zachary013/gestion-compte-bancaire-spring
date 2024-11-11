package org.lsi.services;

import org.lsi.metier.DashboardMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
public class DashboardRestService {

    @Autowired
    private DashboardMetier dashboardMetier;

    @GetMapping("/quantities")
    public Map<String, Long> getQuantities() {
        return dashboardMetier.getQuantities();
    }
}
