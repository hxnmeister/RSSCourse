package com.ua.project.rsscourse.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Currency {
    private String ccy;
    private String base_ccy;
    private double buy;
    private double sale;
}
