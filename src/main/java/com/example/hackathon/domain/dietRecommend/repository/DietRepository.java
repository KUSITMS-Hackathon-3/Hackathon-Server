package com.example.hackathon.domain.dietRecommend.repository;

import com.example.hackathon.domain.dietRecommend.entity.DietRecommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietRepository extends JpaRepository<DietRecommend, Long> {

}
