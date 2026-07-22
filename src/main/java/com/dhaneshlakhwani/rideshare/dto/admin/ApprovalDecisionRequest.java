package com.dhaneshlakhwani.rideshare.dto.admin;

import lombok.Data;

@Data
public class ApprovalDecisionRequest {
    private String action;
    private String remarks;
}
