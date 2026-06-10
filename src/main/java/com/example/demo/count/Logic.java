// sonar remediation trigger 5
package com.example.demo.count;

public class Logic {

    private String sonarTriggerIssue = "temporary sonar trigger";

    public static double countTotal(double price, int quantity) {
        double res = price * quantity;
        return res;
    }
}
