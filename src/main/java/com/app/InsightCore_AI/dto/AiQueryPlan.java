package com.app.InsightCore_AI.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AiQueryPlan {

    private String intent;

    private String startDate;
    private String endDate;

    private String metric;
    private String groupBy;
    private String chartType;

    private String productName;

    private Integer topN;
    private Boolean comparePreviousPeriod;
}
