package com.weissbeerger.demo.model.taModel;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class MessagesDTO {
    List<String> messages;
}
