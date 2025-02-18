package com.vietpq.JobHunter.service.company;

import com.vietpq.JobHunter.dto.page.ResultPaginationDTO;
import com.vietpq.JobHunter.entity.Company;
import com.vietpq.JobHunter.exception.DuplicatedException;
import com.vietpq.JobHunter.exception.NotFoundException;
import com.vietpq.JobHunter.exception.message.CompanyMessage;
import com.vietpq.JobHunter.respository.CompanyRepository;
import com.vietpq.JobHunter.util.validator.CompanyValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company handleCreateCompany(Company company) {
        CompanyValidator.notNullName(company.getName());
        if (companyRepository.existsByName(company.getName())) {
            throw new DuplicatedException(CompanyMessage.NAME_ALREADY_EXIST);
        }
        return companyRepository.save(company);
    }

    @Override
    public Company fetchCompanyById(long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CompanyMessage.NOT_FOUND));
    }

    @Override
    public ResultPaginationDTO fetchAllCompany(Specification<Company> specification, Pageable pageable) {
        Page<Company> companyPage = companyRepository.findAll(specification, pageable);

        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta(
                pageable.getPageNumber() + 1,
                pageable.getPageSize(),
                companyPage.getTotalPages(),
                companyPage.getTotalElements()
        );

        return new ResultPaginationDTO(meta, companyPage.getContent());
    }

    @Override
    public Company handleUpdateCompany(Company company) {
        Company companyUpdate = fetchCompanyById(company.getId());
        companyUpdate.setName(company.getName());
        companyUpdate.setAddress(company.getAddress());
        companyUpdate.setDescription(company.getDescription());
        companyUpdate.setLogo(company.getLogo());
        return companyRepository.save(companyUpdate);
    }

    @Override
    public void handleDeleteCompany(long id) {
        Company company = fetchCompanyById(id);
        companyRepository.delete(company);
    }
}
