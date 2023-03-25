package com.example.hackathon.domain.certification.service;

import com.example.hackathon.domain.certification.dto.CertificationDateDto;
import com.example.hackathon.domain.certification.dto.CertificationInsertDto;
import com.example.hackathon.domain.certification.dto.CertificationMonthDto;

import java.util.List;

public interface CertificationService {

    void save(CertificationInsertDto certificationInsertDto) throws Exception;
    CertificationDateDto findDateCertification(CertificationDateDto certificationDto);
    List<String> findAllCertification(CertificationMonthDto certificationMonthDto);

}
