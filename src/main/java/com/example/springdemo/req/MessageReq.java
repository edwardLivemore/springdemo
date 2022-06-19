package com.example.springdemo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class MessageReq {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("时间戳")
    private LocalDateTime dateTime;

    @ApiModelProperty("消息内容")
    private String content;
}
