package com.vietpq.JobHunter.controller;

import com.vietpq.JobHunter.entity.Company;
import com.vietpq.JobHunter.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @PostMapping("/companies")
    public ResponseEntity<Company> createCompany(@RequestBody Company  company){
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.handleCreateCompany(company));
    }
    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable("id") long id){
        return ResponseEntity.ok(companyService.fetchCompanyById(id).get());
    }
    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getAllCompany(){
        return ResponseEntity.ok(companyService.fetchAllCompany());
    }
    @PutMapping("/companies")
    public ResponseEntity<Company> updateCompany(@RequestBody Company company){
        return ResponseEntity.ok(companyService.handleUpdateCompany(company));
    }
    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable("id") long id){
        companyService.handleDeleteCompany(id);
        return ResponseEntity.ok(null);
    }

}
