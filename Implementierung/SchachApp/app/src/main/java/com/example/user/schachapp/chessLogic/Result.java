package com.example.user.schachapp.chessLogic;

public class Result {
    private String reason;
    private String result;
    public Result(String result, String reason) {
        this.reason = reason;
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public String getResult() {
        return result;
    }
}
