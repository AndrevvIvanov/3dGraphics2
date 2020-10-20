package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Render {

    static double[][] z_buffer = new double[Main.w][Main.h];

    {
        for (int i = 0; i < Main.h; i++) {
            for (int j = 0; j < Main.w; j++) {
                z_buffer[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }


    public static void render(BufferedImage img, ArrayList<V[][]> figures) {

        for (int i = 0; i < figures.size(); i++) {
            V v1 = figures.get(i)[0][0];
            V v2 = figures.get(i)[1][0];
            V v3 = figures.get(i)[2][0];
            V normal1 = figures.get(i)[0][2];
            V normal2 = figures.get(i)[1][2];
            V normal3 = figures.get(i)[2][2];

            double alpha = -91 * (Math.PI / 180);
            double beta = 90 * (Math.PI / 180);
            double gamma = 180 * (Math.PI / 180);

            v1 = rotate(alpha, beta, gamma, v1);
            v2 = rotate(alpha, beta, gamma, v2);
            v3 = rotate(alpha, beta, gamma, v3);
            normal1 = rotate(alpha, beta, gamma, normal1);
            normal2 = rotate(alpha, beta, gamma, normal2);
            normal3 = rotate(alpha, beta, gamma, normal3);

            V a = v2.sub(v1);
            V b = v3.sub(v1);
            V t = a.crossProduct(b);
            t = t.scalarMult(1 / t.norm());
            V l = new V(new double[]{0, 0, -1});
            double check = t.scalarProduct(l);
            double n1 = normal1.scalarProduct(l);
            double n2 = normal2.scalarProduct(l);
            double n3 = normal3.scalarProduct(l);

            if (check >= 0) {
                V v = new V(new double[]{500, 550, 0});
                v1 = v1.sum(v);
                v2 = v2.sum(v);
                v3 = v3.sum(v);
                Triangle(img, v1, v2, v3,  n1, n2, n3);
                //Triangle(img, v1, v2, v3, new Color(0, 0, 0));
            }
        }
    }


    public static V rotate(double alpha, double beta, double gamma, V v) {
        Matrix Rx = new Matrix(new double[][]
                {{1, 0, 0},
                        {0, Math.cos(alpha), -Math.sin(alpha)},
                        {0, Math.sin(alpha), Math.cos(alpha)}});
        Matrix Ry = new Matrix(new double[][]
                {{Math.cos(beta), 0, Math.sin(beta)},
                        {0, 1, 0},
                        {-Math.sin(beta), 0, Math.cos(beta)}});
        Matrix Rz = new Matrix(new double[][]
                {{Math.cos(gamma), -Math.sin(gamma), 0},
                        {Math.sin(gamma), Math.cos(gamma), 0},
                        {0, 0, 1}});
        Matrix R = Rx.Mult((Ry.Mult(Rz)));
        return R.vectorMult(v);
    }


    public static void renderLine(BufferedImage img, int x1, int y1, int x2, int y2, Color c) {
        if (x1 == x2 && y1 == y2) {
            img.setRGB(x1, y2, c.getRGB());
            return;
        }
        if (Math.abs(x1 - x2) > Math.abs(y1 - y2)) {
            if (x1 < x2) {
                for (int i = x1; i <= x2; i++) {
                    int y = (int) ((i - x1) * (y2 - y1) / (x2 - x1)) + y1;
                    img.setRGB(i, y, c.getRGB());
                }
            }

            if (x1 >= x2) {
                for (int i = x2; i <= x1; i++) {
                    int y = (int) ((i - x1) * (y2 - y1) / (x2 - x1)) + y1;
                    img.setRGB(i, y, c.getRGB());
                }
            }
        }

        if (Math.abs(y1 - y2) >= Math.abs(x1 - x2)) {
            if (y1 < y2) {
                for (int i = y1; i <= y2; i++) {
                    int x = (int) (((i - y1) * (x2 - x1) / (y2 - y1)) + x1);
                    img.setRGB(x, i, c.getRGB());

                }
            }

            if (y1 >= y2) {
                for (int i = y2; i <= y1; i++) {
                    int x = (int) ((i - y1) * (x2 - x1) / (y2 - y1)) + x1;
                    img.setRGB(x, i, c.getRGB());
                }
            }
        }
    }


    public static void Triangle(BufferedImage img, V v1, V v2, V v3, double n1, double n2, double n3) {
        double x1 = v1.arr[0];
        double y1 = v1.arr[1];
        double z1 = v1.arr[2];

        double x2 = v2.arr[0];
        double y2 = v2.arr[1];
        double z2 = v2.arr[2];

        double x3 = v3.arr[0];
        double y3 = v3.arr[1];
        double z3 = v3.arr[2];

        int minx = (int) Math.min(x1, x2);
        minx = (int) Math.min(x3, minx);
        minx = Math.max(Math.min(Main.w - 1, minx), 0);
        int maxx = (int) Math.max(x1, x2);
        maxx = (int) Math.max(x3, maxx);
        maxx = Math.min(Math.max(0, maxx), 1365);

        int miny = (int) Math.min(y1, y2);
        miny = (int) Math.min(y3, miny);
        miny = Math.max(Math.min(Main.h - 1, miny), 0);
        int maxy = (int) Math.max(y1, y2);
        maxy = (int) Math.max(y3, maxy);
        maxy = Math.min(Math.max(0, maxy), 767);

        for (int i = minx; i <= maxx; i++) {
            for (int j = miny; j <= maxy; j++) {
                V2 A = new V2(x1, y1);
                V2 B = new V2(x2, y2);
                V2 C = new V2(x3, y3);
                V2 P = new V2(i, j);

                V2 AB = B.sub(A);
                V2 AC = C.sub(A);
                V2 PA = A.sub(P);

                double u = (AC.x * PA.y - PA.x * AC.y) / (AB.x * AC.y - AC.x * AB.y);
                double v = (PA.x * AB.y - AB.x * PA.y) / (AB.x * AC.y - AC.x * AB.y);

                double z = z2 * u + z3 * v + z1 * (1 - u - v);
                double n = n1 * (1 - u - v) + n2 * u + n3 * v;
                n = Math.max(0, n);

                if (u + v <= 1 && u >= 0 && v >= 0 && z_buffer[i][j] >= z) {
                    img.setRGB(i, j, new Color((int) (n * 255), (int) (n * 255), (int) (n * 255)).getRGB());
                    z_buffer[i][j] = z;
                }
            }
        }
    }


    public static void renderCircle(BufferedImage img, int x, int y, int k, int d) {
        for (int i = 0; i < k; i++) {
            renderLine(img, x, y, (int) (d * Math.cos(Math.PI * i / (k / 2)) + x), (int) (-d * Math.sin(Math.PI * i / (k / 2)) + y), new Color(0, 0, 0));
        }
    }


    public static void renderTriangle(BufferedImage img, int x1, int y1, int x2, int y2, int x3, int y3, Color c) {
        int minx = Math.min(x1, x2);
        minx = Math.min(x3, minx);
        int maxx = Math.max(x1, x2);
        maxx = Math.max(x3, maxx);

        int miny = Math.min(y1, y2);
        miny = Math.min(y3, miny);
        int maxy = Math.max(y1, y2);
        maxy = Math.max(y3, maxy);

        for (int i = minx; i <= maxx; i++) {
            for (int j = miny; j <= maxy; j++) {
                V2 A = new V2(x1, y1);
                V2 B = new V2(x2, y2);
                V2 C = new V2(x3, y3);
                V2 P = new V2(i, j);

                V2 AB = B.sub(A);
                V2 AC = C.sub(A);
                V2 PA = A.sub(P);

                double u = (AC.x * PA.y - PA.x * AC.y) / (AB.x * AC.y - AC.x * AB.y);
                double v = (PA.x * AB.y - AB.x * PA.y) / (AB.x * AC.y - AC.x * AB.y);
                if (u + v <= 1 && u >= 0 && v >= 0) {
                    img.setRGB(i, j, c.getRGB());
                }
            }
        }
    }


    public static void renderLineTriangle(BufferedImage img, int x1, int y1, int x2, int y2, int x3, int y3, Color c) {
        renderLine(img, x1, y1, x2, y2, c);
        renderLine(img, x2, y2, x3, y3, c);
        renderLine(img, x3, y3, x1, y1, c);
    }


    public static void renderPolygon(BufferedImage img, int x, int y, int k, int d) {
        for (int i = 0; i < k; i++) {
            renderTriangle(img, x, y, (int) (d * Math.cos((2 * Math.PI * i) / k) + x), (int) (d * Math.sin((2 * Math.PI * i) / k) + y), (int) (d * Math.cos((2 * Math.PI * (i + 1)) / k) + x), (int) (d * Math.sin((2 * Math.PI * (i + 1)) / k) + y), new Color(255 - 255 * i / k, 255 - 255 * i / k, 255 * i / k));
        }
    }


}