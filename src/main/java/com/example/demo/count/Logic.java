package com.example.demo.count;

public class Logic {
private Logic() {
    }
public static double countTotal(double price, int quantity) {
        return price * quantity;
    }
//this is test
    public static double applyDiscount(double amount, boolean premiumCustomer) {
        if (premiumCustomer) {
            return amount * 0.90;
        }
        return amount;
    }

    public static double sum(double amount, boolean premiumCustomer) {
        if (premiumCustomer) {
            return amount + 0.90;
        }
        return amount;
    }
}