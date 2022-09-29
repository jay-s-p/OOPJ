package com.javabank.bank;

public class DatabaseException extends Exception {
    public DatabaseException(String str) {
        super("DATABASE Error: " + str);
    }
}
