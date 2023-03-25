package com.example.hackathon.domain.notice.repository;

import com.example.hackathon.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
