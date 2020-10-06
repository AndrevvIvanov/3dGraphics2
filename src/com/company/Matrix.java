package com.company;

import java.util.Arrays;

public class Matrix {
    double arr[][];

    @Override
    public String toString() {
        String s = new String();
        for (int i = 0; i < arr.length; i++) {
            s = s + Arrays.toString(arr[i]) + " ";
        }
        return s;
    }

    public Matrix(double[][] arr) {
        this.arr = arr;
    }

    public Matrix sum(Matrix m) {
        double new_arr[][] = new double[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                new_arr[i][j] = arr[i][j] + m.arr[i][j];
            }
        }

        return new Matrix(new_arr);
    }

    public Matrix sub(Matrix m) {
        double new_arr[][] = new double[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                new_arr[i][j] = arr[i][j] - m.arr[i][j];
            }
        }
        return new Matrix(new_arr);
    }

    public Matrix scalarMult(double a) {
        double new_arr[][] = new double[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                new_arr[i][j] = arr[i][j] * a;
            }
        }
        return new Matrix(new_arr);
    }

    public V vectorMult(V v) {
        double new_arr[] = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                new_arr[i] += arr[i][j] * v.arr[j];
            }
        }
        return new V(new_arr);
    }

    public Matrix Mult(Matrix m) {
        double new_arr[][] = new double[arr.length][arr.length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                for (int k = 0; k < arr.length; k++) {
                    new_arr[i][j] += arr[i][k] * m.arr[k][j];
                }
            }
        }
        return new Matrix(new_arr);
    }

}
