package com.example.hackathon.domain.recipe.dto;

import com.example.hackathon.domain.rcontent.entity.Rcontent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RcontentDto {
    private String element;
    private int orderIdx;

    public static RcontentDto of(Rcontent rcontent) {
        return RcontentDto.builder()
                .element(rcontent.getElement())
                .orderIdx(rcontent.getOrderIdx())
                .build();
    }
}
