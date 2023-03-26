package com.yty.junit.service;

import org.junit.Assert;
import org.junit.Test;


public class MathServiceTest {
    @Test
    public void Testadd(){
        MathService mathService = new MathService();
        int actual = mathService.add(1,3);
        int except = 4;
        Assert.assertEquals(actual, except);

    }
    @Test
    public void Testsub(){
        MathService mathService = new MathService();
        int actual = mathService.sub(1, 3);
        int except = -2;
        Assert.assertEquals(except, actual);
    }
    @Test
    public void Testmul(){
        int actual = new MathService().mul(1, 3);
        int except = 3;
        Assert.assertEquals(except, actual);
    }
    @Test
    public void TestDiv(){
        double actual = new MathService().div(30, 3);
        double except = 10;
        Assert.assertEquals(except, actual, 0.1);
    }
}
