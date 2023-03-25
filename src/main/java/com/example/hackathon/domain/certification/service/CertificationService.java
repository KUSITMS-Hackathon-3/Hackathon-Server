package com.example.hackathon.domain.certification.service;

import com.example.hackathon.domain.certification.dto.CertificationDateDto;
import com.example.hackathon.domain.certification.dto.CertificationInsertDto;
import com.example.hackathon.domain.certification.dto.CertificationMonthDto;
import com.example.hackathon.domain.certification.entity.Certification;
import com.example.hackathon.domain.certification.repository.CertificationRepository;
import com.example.hackathon.domain.user.entity.User;
import com.example.hackathon.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.DateFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public interface CertificationService {

    void save(CertificationInsertDto certificationInsertDto) throws Exception;
    CertificationDateDto findDateCertification(CertificationDateDto certificationDto);
    List<String> findAllCertification(CertificationMonthDto certificationMonthDto);

}
