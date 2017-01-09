package org.humanResources.employment.repository;


import org.humanResources.employment.model.Employment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("employmentRepository")
public interface EmploymentRepository extends JpaRepository<Employment, Long> {

}