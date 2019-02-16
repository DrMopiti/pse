package com.example.user.schachapp.chessLogic;

/**
 * A result, consisting of two seperate strings.
 */
public class Result {
    private String reason;
    private String result;

    /**
     * Creates a result from the given strings
     * @param result the result itself
     * @param reason a additional reason for the result
     */
    public Result(String result, String reason) {
        this.reason = reason;
        this.result = result;
    }

    /**
     *
     * @return the reason string
     */
    public String getReason() {
        return reason;
    }

    /**
     *
     * @return the result string
     */
    public String getResult() {
        return result;
    }
}
