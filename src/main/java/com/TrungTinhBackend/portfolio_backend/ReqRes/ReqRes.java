package com.TrungTinhBackend.portfolio_backend.ReqRes;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReqRes {

    private Long statusCode;

    private String token;

    private String message;

    private Object data;

    private LocalDateTime timestamp;

    public ReqRes(long l, String s, LocalDateTime now, Object o) {
    }

    public ReqRes() {

    }

}
