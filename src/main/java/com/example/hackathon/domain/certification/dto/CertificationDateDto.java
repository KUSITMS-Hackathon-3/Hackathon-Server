package com.example.hackathon.domain.certification.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CertificationDateDto {
    private String date;

    List<Map<String, String>> contents = new ArrayList<>();
}
