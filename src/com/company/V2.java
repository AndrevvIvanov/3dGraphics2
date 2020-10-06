package com.company;

public class V2 {
    double x;
    double y;

    @Override
    public String toString() {
        return "V2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public V2(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public V2 sum(V2 v2) {
        V2 ans = new V2(x + v2.x, y + v2.y);
        return ans;
    }

    public V2 sub(V2 v2) {
        V2 ans = new V2(x - v2.x, y - v2.y);
        return ans;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public V2 nmMul(double nm) {
        V2 ans = new V2(x * nm, y * nm);
        return ans;
    }

    public V2 nmDiv(double nm) {
        V2 ans = new V2(x / nm, y / nm);
        return ans;
    }

    public V2 subt(V2 v2) {
        V2 ans = new V2(x - v2.x, y - v2.y);
        return ans;
    }

    public V2 rot(double anglespeed) {
        double a = x;
        double b = y;
        double n = (a * Math.cos(anglespeed) - b * Math.sin(anglespeed));
        double m = (a * Math.sin(anglespeed) + b * Math.cos(anglespeed));
        return new V2(n, m);
    }
}
