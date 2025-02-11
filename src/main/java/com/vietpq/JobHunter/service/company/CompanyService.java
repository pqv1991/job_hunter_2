package com.vietpq.JobHunter.service.company;

import com.vietpq.JobHunter.entity.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    Company handleCreateCompany(Company company);
    Optional<Company> fetchCompanyById(long id);
    List<Company> fetchAllCompany();
    Company handleUpdateCompany(Company company);
    void handleDeleteCompany(long id);
}
