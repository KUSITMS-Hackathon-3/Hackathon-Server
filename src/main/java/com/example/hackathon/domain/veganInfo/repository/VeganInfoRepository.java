package com.example.hackathon.domain.veganInfo.repository;

import com.example.hackathon.domain.veganInfo.entity.VeganInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeganInfoRepository extends JpaRepository<VeganInfo, Long> {

}
