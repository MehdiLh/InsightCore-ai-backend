package com.app.InsightCore_AI.dto;

import java.time.LocalDate;

public record DateRange(
        LocalDate start,
        LocalDate end
) {}