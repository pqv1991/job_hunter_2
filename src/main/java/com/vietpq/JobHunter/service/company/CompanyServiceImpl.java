package com.vietpq.JobHunter.service.company;

import com.vietpq.JobHunter.entity.Company;
import com.vietpq.JobHunter.exception.DuplicatedException;
import com.vietpq.JobHunter.exception.NotFoundException;
import com.vietpq.JobHunter.exception.message.CompanyMessage;
import com.vietpq.JobHunter.respository.CompanyRepository;
import com.vietpq.JobHunter.util.validator.CompanyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService{
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company handleCreateCompany(Company company) {
        CompanyValidator.notNullName(company.getName());
        if(companyRepository.existsByName(company.getName())){
            throw  new DuplicatedException(CompanyMessage.NAME_ALREADY_EXIST);
        }
        return companyRepository.save(company);
    }

    @Override
    public Optional<Company> fetchCompanyById(long id) {
        Optional<Company>  companyOptional = companyRepository.findById(id);
        if(companyOptional.isEmpty())
            throw  new NotFoundException(CompanyMessage.NOT_FOUND);
        return companyOptional;
    }

    @Override
    public List<Company> fetchAllCompany() {
        List<Company> companyList = companyRepository.findAll();
        if(companyList.isEmpty())
            throw  new NotFoundException(CompanyMessage.NOT_FOUND);
        return companyList;
    }

    @Override
    public Company handleUpdateCompany(Company company) {
        Optional<Company> companyOptional = fetchCompanyById(company.getId());
        Company companyUpdate = companyOptional.get();
        companyUpdate.setName(company.getName());
        companyUpdate.setAddress(company.getAddress());
        companyUpdate.setDescription(company.getDescription());
        companyUpdate.setLogo(company.getLogo());
        return companyRepository.save(companyUpdate);
    }

    @Override
    public void handleDeleteCompany(long id) {
        Optional<Company>  companyOptional = fetchCompanyById(id);
        companyRepository.delete(companyOptional.get());
    }
}
