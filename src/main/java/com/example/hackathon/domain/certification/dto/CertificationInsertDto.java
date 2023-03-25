package com.example.hackathon.domain.certification.dto;

import com.example.hackathon.domain.user.entity.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Data
public class CertificationInsertDto {
    private Long userIdx;
    private String imageUrl;
    private String memo;
}
