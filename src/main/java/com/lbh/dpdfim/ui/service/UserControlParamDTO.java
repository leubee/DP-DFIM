package com.lbh.dpdfim.ui.service;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserControlParamDTO {
    private Double minSupport;

    private Double minConfidence;

    private Integer numPartitions;

    private Boolean differentialPrivacy;
}
