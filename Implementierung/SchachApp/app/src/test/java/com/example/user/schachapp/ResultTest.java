package com.example.user.schachapp;

import com.example.user.schachapp.chessLogic.Result;

import org.junit.*;

import static org.junit.Assert.*;


public class ResultTest {
    static Result result;

    @BeforeClass
    public static void setResult() {
        result = new Result("1:0", "Matt");
    }

    @Test
    public void testResult() {
        assertEquals(result.getResult(), "1:0");
    }

    @Test
    public void testReason() {
        assertEquals(result.getReason(), "Matt");
    }
}