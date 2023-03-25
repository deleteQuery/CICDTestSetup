package com.training.queue.pojo.extensions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueueAttribute {
    private String attributeName;
//    private Boolean isAttributeTokenized; @todo for masking secret data
//    private QueueAttributeType dataType; @todo For future support of validations
}
