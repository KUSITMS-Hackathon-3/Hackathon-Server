package com.example.hackathon.domain.notice.service;

import com.example.hackathon.domain.notice.entity.Notice;
import com.example.hackathon.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository noticeRepository;

    @Override
    public List<Notice> getNotice() {
        return this.noticeRepository.findAll();
    }
}
