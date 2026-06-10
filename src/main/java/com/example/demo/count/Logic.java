package com.example.demo.count;

public class Logic {

    public static double countTotal(double price, int quantity) {
        double res = price * quantity;
        return res;
    }

    public static double applyDiscount(double amount, boolean premiumCustomer) {
        if (premiumCustomer) {
            return amount * 0.90;
        }
        return amount;
    }
}