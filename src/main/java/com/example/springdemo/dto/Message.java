package com.example.springdemo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("时间戳")
    private Long timeStamp;

    @ApiModelProperty("消息内容")
    private String content;
}
