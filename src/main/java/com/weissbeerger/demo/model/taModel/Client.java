package com.weissbeerger.demo.model.taModel;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Client {
    String clientId;
    long updateTime;
    String ip;
    String agent;
    String screenResolution;
    long clientLocalTime;
    String timeZone;
    Status status;
}
