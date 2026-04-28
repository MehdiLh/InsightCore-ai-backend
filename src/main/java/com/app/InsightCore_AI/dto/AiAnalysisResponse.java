package com.app.InsightCore_AI.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AiAnalysisResponse {

    private String trend;
    private String problem;
    private List<String> possibleCauses;
    private List<String> actions;
    private List<String> experiments;
    private ChartData chartData;

}
