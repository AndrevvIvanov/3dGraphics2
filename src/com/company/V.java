package com.company;

import java.util.Arrays;

public class V {
    double arr[];

    @Override
    public String toString() {
        return "v=" + Arrays.toString(arr);
    }

    public V(double arr[]) {
        this.arr = arr;
    }

    public V sum(V v) {
        double new_arr[] = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            new_arr[i] = arr[i] + v.arr[i];
        }
        return new V(new_arr);
    }

    public V sub(V v) {
        double new_arr[] = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            new_arr[i] = arr[i] - v.arr[i];
        }
        return new V(new_arr);
    }

    public V scalarMult(double a) {
        double new_arr[] = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            new_arr[i] = arr[i] * a;
        }

        return new V(new_arr);
    }

    public double scalarProduct(V v) {
        double res = 0;
        for (int i = 0; i < arr.length; i++) {
            res += arr[i] * v.arr[i];
        }
        return res;
    }

    public V crossProduct(V v) {
        if (arr.length == v.arr.length && arr.length == 3) {
            return new V(new double[]{arr[1] * v.arr[2] - arr[2] * v.arr[1], arr[2] * v.arr[0] - arr[0] * v.arr[2], arr[0] * v.arr[1] - arr[1] * v.arr[0]});
        }
        return null;
    }

    public double norm() {
        double a = 0;
        for (int i = 0; i < arr.length; i++) {
            a += arr[i] * arr[i];
        }
        return Math.sqrt(a);
    }

    public void normalize() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i] / norm();
        }
    }


}