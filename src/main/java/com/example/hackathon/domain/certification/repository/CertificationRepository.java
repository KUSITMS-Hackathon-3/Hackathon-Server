package com.example.hackathon.domain.certification.repository;

import com.example.hackathon.domain.certification.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
    List<Certification> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
