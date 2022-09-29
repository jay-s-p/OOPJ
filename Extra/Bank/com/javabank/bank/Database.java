package com.javabank.bank;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Database {

    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Account> accounts = new ArrayList<>();

    public Database() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("users.ser"));
            users = ((ArrayList<User>) in.readObject());
            in.close();

            in = new ObjectInputStream(new FileInputStream("accounts.ser"));
            accounts = ((ArrayList<Account>) in.readObject());
            in.close();
        } catch (Exception e) {
        }
    }

    public Boolean usernameAvailable(String s) {
        for (User u : users) {
            if (u.checkUsername(s))
                return false;
        }
        return true;
    }

    protected void updateAccount(Account acc) throws DatabaseException {
        int acc_no = acc.getAccountNumber();

        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).checkAccountNo(acc_no)) {
                accounts.set(i, acc);
            }
        }

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("accounts.ser"));
            out.writeObject(accounts);
            out.close();
        } catch (Exception e) {
            throw new DatabaseException("Failed to update Account");
        }
    }

    public Account getAccount(int accNo) {
        for (Account acc : accounts) {
            if (acc.checkAccountNo(accNo)) {
                return acc;
            }
        }
        return null;
    }

    public User loginValidate(String username, String password) {
        for (User user : users) {
            if (user.validate(username, password)) {
                return user;
            }
        }
        return null;
    }

    public void addData(User user, Account acc) throws DatabaseException {
        users.add(user);
        if (acc != null)
            accounts.add(acc);
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("users.ser"));
            out.writeObject(users);
            out.close();
            if (acc == null)
                return;
            out = new ObjectOutputStream(new FileOutputStream("accounts.ser"));
            out.writeObject(accounts);
            out.close();
        } catch (Exception e) {
            throw new DatabaseException("Failed to update Account ");
        }
    }
}
