package com.javabank.bank;

import java.util.Random;

public class Captcha {
    private String keys = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String captcha = "";

    public String generateCaptcha() {
        captcha = "";
        for (int i = 0; i < 5; i++) {
            captcha += keys.charAt(new Random().nextInt(keys.length()));
        }
        return captcha;
    }

    public boolean validateCaptcha(String x) {
        if (captcha.equals(x))
            return true;
        return false;
    }
}