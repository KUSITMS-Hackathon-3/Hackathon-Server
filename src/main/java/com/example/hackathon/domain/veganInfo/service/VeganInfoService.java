package com.example.hackathon.domain.veganInfo.service;

import com.example.hackathon.domain.veganInfo.dto.VeganInfoDto;
import com.example.hackathon.domain.veganInfo.entity.VeganInfo;
import com.example.hackathon.domain.veganInfo.repository.VeganInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class VeganInfoService {
    private final VeganInfoRepository veganInfoRepository;
    Random rd = new Random();

    public VeganInfoDto getKnowledge() {
        int idx = rd.nextInt(10)+1;
        VeganInfo byId = veganInfoRepository.findById((long) idx).orElseThrow();
        VeganInfoDto veganInfoDto = new VeganInfoDto(byId.getTitle(), byId.getComment());
        return veganInfoDto;
    }
}
