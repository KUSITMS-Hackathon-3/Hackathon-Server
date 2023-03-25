package com.example.hackathon.domain.certification.service;

import com.example.hackathon.domain.certification.dto.CertificationInsertDto;
import com.example.hackathon.domain.certification.entity.Certification;
import com.example.hackathon.domain.certification.repository.CertificationRepository;
import com.example.hackathon.domain.user.entity.User;
import com.example.hackathon.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

}
