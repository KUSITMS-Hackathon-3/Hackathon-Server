package com.example.hackathon.domain.dietRecommend.service;

import com.example.hackathon.domain.dietRecommend.entity.DietRecommend;
import com.example.hackathon.domain.dietRecommend.repository.DietRepository;
import com.example.hackathon.domain.veganInfo.dto.VeganInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class DietServiceImpl implements DietService{

    private final DietRepository dietRepository;

    @Override
    public DietRecommend getDiet(){
        Random rd = new Random();
        int idx = rd.nextInt(10)+1;
        DietRecommend dietRecommend = dietRepository.findById((long) idx).orElseThrow();
        return dietRecommend;
    }
}
