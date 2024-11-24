package com.TrungTinhBackend.portfolio_backend.ReqRes;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReqRes {

    private Long statusCode;

    private String message;

    private Object data;

    private LocalDateTime timestamp;
}