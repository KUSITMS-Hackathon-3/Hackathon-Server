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

@Service
@RequiredArgsConstructor
public class CertificationService {
    private final CertificationRepository certificationRepository;
    private final UserRepository userRepository;

    public void save(CertificationInsertDto certificationInsertDto) throws Exception {
        Optional<User> optionalUser = userRepository.findById(certificationInsertDto.getUserIdx());
        if (optionalUser.isEmpty())
            throw new Exception();
        User user = optionalUser.get();

        Certification certification = Certification.builder()
                .user(user)
                .imageUrl(certificationInsertDto.getImageUrl())
                .memo(certificationInsertDto.getMemo())
                .build();

        certificationRepository.save(certification);

        /**
         * 필요 :
         * 인증시 점수 업
         */
    }

    public CertificationDateDto findDateCertification(CertificationDateDto certificationDto) {
        String startDay = certificationDto.getDate() + "-00-00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");

        LocalDateTime start = LocalDateTime.parse(startDay, formatter);
        LocalDateTime end = start.plusDays(1L);

        List<Certification> certifications = certificationRepository.findAllByCreatedAtBetween(start, end);
        List<Map<String, String>> maps = new ArrayList<>();

        for (Certification certification : certifications) {
            Map<String, String> map = new HashMap<>();
            map.put("imageUrl", certification.getImageUrl());
            map.put("memo", certification.getMemo());
            maps.add(map);
        }

        CertificationDateDto certificationDateDto = CertificationDateDto.builder()
                .date(startDay)
                .contents(maps)
                .build();
        return certificationDateDto;
    }

    public List<String> findAllCertification(CertificationMonthDto certificationMonthDto) {
        String parseMonth = certificationMonthDto.getMonth() + "-01-00-00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");

        LocalDateTime start = LocalDateTime.parse(parseMonth, formatter);
        LocalDateTime end = start.plusMonths(1L);

        List<Certification> certifications = certificationRepository.findAllByCreatedAtBetween(start, end);

        List<String> dates = new ArrayList<>();
        for (Certification certification : certifications) {
            LocalDateTime createdAt = certification.getCreatedAt();
            String date = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dates.add(date);
        }

        return dates;
    }

}
