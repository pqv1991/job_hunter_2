package com.vietpq.JobHunter.service.company;

import com.vietpq.JobHunter.dto.page.ResultPaginationDTO;
import com.vietpq.JobHunter.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    Company handleCreateCompany(Company company);
    Company fetchCompanyById(long id);
    ResultPaginationDTO fetchAllCompany(Specification<Company> specification, Pageable pageable);
    Company handleUpdateCompany(Company company);
    void handleDeleteCompany(long id);
}
