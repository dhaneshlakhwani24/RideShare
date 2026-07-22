package com.dhaneshlakhwani.rideshare.dto.ride;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class LocationUpdateRequest {
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Boolean online;
}
