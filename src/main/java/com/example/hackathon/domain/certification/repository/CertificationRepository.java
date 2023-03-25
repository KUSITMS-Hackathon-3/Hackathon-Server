package com.example.hackathon.domain.certification.repository;

import com.example.hackathon.domain.certification.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificationRepository extends JpaRepository<Certification, Long> {

}
