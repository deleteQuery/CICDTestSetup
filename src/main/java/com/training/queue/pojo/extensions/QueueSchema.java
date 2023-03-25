package com.training.queue.pojo.extensions;

import com.training.queue.pojo.extensions.QueueAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueueSchema {
    List<QueueAttribute> queueAttributeList;
}
