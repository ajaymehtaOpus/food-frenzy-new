package com.example.demo.count;

public class Logic {

    public static double countTotal(double price, int quantity) {
        double res = price * quantity;
        return res;
    }
//this is test
    public static double applyDiscount(double amount, boolean premiumCustomer) {
        if (premiumCustomer) {
            return amount * 0.90;
        }
        return amount;
    }

    public static double Sum(double amount, boolean premiumCustomer) {
        if (premiumCustomer) {
            return amount + 0.90;
        }
        return amount;
    }
}