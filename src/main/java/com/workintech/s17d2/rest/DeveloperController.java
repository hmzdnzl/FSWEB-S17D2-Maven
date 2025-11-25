package com.workintech.s17d2.rest;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.workintech.s17d2.model.Developer;
import com.workintech.s17d2.model.Experience;
import com.workintech.s17d2.tax.Taxable;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/developers")
public class DeveloperController {
    private Map<Integer, Developer> developers;
    private final Taxable taxable;


    @PostConstruct
    public void initDevelopers() {
        developers = new HashMap<>();
    }
   
    public void init() {
        initDevelopers();
    }

    public Map<Integer, Developer> getDevelopers() {
    return developers;
}

    public DeveloperController(Taxable taxable) {
        this.taxable = taxable;
    }
    
    @GetMapping("/workintech/developers")
    public List<Developer> getAllDevelopers() {
    return new ArrayList<>(developers.values());
}

    @GetMapping("/workintech/developers/{id}")
    public Developer getDeveloperById(@PathVariable int id) {
        return developers.get(id);
    }
    
    @PostMapping("/workintech/developers")
    public void addDeveloper(@RequestBody Developer developer) {
        Developer newDev;
        double salary = developer.getSalary();
        int id = developer.getId();
        if (developer.getExperience() == Experience.JUNIOR) {
            double tax = salary * taxable.getSimpleTaxRate() / 100.0;
            newDev = new com.workintech.s17d2.model.JuniorDeveloper(id, developer.getName(), salary - tax);
        } else if (developer.getExperience() == Experience.MID) {
            double tax = salary * taxable.getMiddleTaxRate() / 100.0;
            newDev = new com.workintech.s17d2.model.MidDeveloper(id, developer.getName(), salary - tax);
        } else {
            double tax = salary * taxable.getUpperTaxRate() / 100.0;
            newDev = new com.workintech.s17d2.model.SeniorDeveloper(id, developer.getName(), salary - tax);
        }
        developers.put(newDev.getId(), newDev);
    }

    @PutMapping("/workintech/developers/{id}")
    public void updateDeveloper(@PathVariable int id, @RequestBody Developer developer) {
        Developer updatedDev;
        double salary = developer.getSalary();
        int devId = developer.getId();
        if (developer.getExperience() == Experience.JUNIOR) {
            double tax = salary * taxable.getSimpleTaxRate() / 100.0;
            updatedDev = new com.workintech.s17d2.model.JuniorDeveloper(devId, developer.getName(), salary - tax);
        } else if (developer.getExperience() == Experience.MID) {
            double tax = salary * taxable.getMiddleTaxRate() / 100.0;
            updatedDev = new com.workintech.s17d2.model.MidDeveloper(devId, developer.getName(), salary - tax);
        } else {
            double tax = salary * taxable.getUpperTaxRate() / 100.0;
            updatedDev = new com.workintech.s17d2.model.SeniorDeveloper(devId, developer.getName(), salary - tax);
        }
        developers.put(id, updatedDev);
    }

}
