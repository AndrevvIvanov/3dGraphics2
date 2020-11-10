package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Render {


    public static void render(BufferedImage img, ArrayList<V[][]> figures, BufferedImage texture, double d_x, double d_y, double d_z, double alpha, double beta, double gamma, double c_x, double c_y, double c_z, double[][] z_buffer) {

        for (int i = 0; i < figures.size(); i++) {
            V v1 = figures.get(i)[0][0];
            V v2 = figures.get(i)[1][0];
            V v3 = figures.get(i)[2][0];
            V texture_coordinates1 = figures.get(i)[0][1];
            V texture_coordinates2 = figures.get(i)[1][1];
            V texture_coordinates3 = figures.get(i)[2][1];
            V normal1 = figures.get(i)[0][2];
            V normal2 = figures.get(i)[1][2];
            V normal3 = figures.get(i)[2][2];

            V v1_help = new V(new double[]{v1.arr[0], v1.arr[1], v1.arr[2], 1});
            V v2_help = new V(new double[]{v2.arr[0], v2.arr[1], v2.arr[2], 1});
            V v3_help = new V(new double[]{v3.arr[0], v3.arr[1], v3.arr[2], 1});
            V normal1_help = new V(new double[]{normal1.arr[0], normal1.arr[1], normal1.arr[2], 1});
            V normal2_help = new V(new double[]{normal2.arr[0], normal2.arr[1], normal2.arr[2], 1});
            V normal3_help = new V(new double[]{normal3.arr[0], normal3.arr[1], normal3.arr[2], 1});
            double w = 0.1;
            double h = 0.1;
            double d = 0.1;
            v1_help = Projection(v1_help.arr[0], v1_help.arr[1], w, h, d).vectorMult(Translocation(d_x, d_y, d_z).vectorMult(Rotation(alpha, beta, gamma).vectorMult(Scale(c_x, c_y, c_z).vectorMult(v1_help))));
            v2_help = Projection(v2_help.arr[0], v2_help.arr[1], w, h, d).vectorMult(Translocation(d_x, d_y, d_z).vectorMult(Rotation(alpha, beta, gamma).vectorMult(Scale(c_x, c_y, c_z).vectorMult(v2_help))));
            v3_help = Projection(v3_help.arr[0], v3_help.arr[1], w, h, d).vectorMult(Translocation(d_x, d_y, d_z).vectorMult(Rotation(alpha, beta, gamma).vectorMult(Scale(c_x, c_y, c_z).vectorMult(v3_help))));
            //normal1_help = Projection(normal1_help.arr[0], normal1_help.arr[1], w, h, d).vectorMult(Translocation(d_x, d_y, d_z).vectorMult(Rotation(alpha, beta, gamma).vectorMult(normal1_help)));
            //normal2_help = Projection(normal2_help.arr[0], normal2_help.arr[1], w, h, d).vectorMult(Translocation(d_x, d_y, d_z).vectorMult(Rotation(alpha, beta, gamma).vectorMult(normal2_help)));
            //normal3_help = Projection(normal3_help.arr[0], normal3_help.arr[1], w, h, d).vectorMult(Translocation(d_x, d_y, d_z).vectorMult(Rotation(alpha, beta, gamma).vectorMult(normal3_help)));

           /* v1_help = Translocation(d_x, d_y, d_z).vectorMult(Rotation(alpha, beta, gamma).vectorMult(Scale(c_x, c_y, c_z).vectorMult(v1_help)));
            v2_help = Translocation(d_x, d_y, d_z).vectorMult(Rotation(alpha, beta, gamma).vectorMult(Scale(c_x, c_y, c_z).vectorMult(v2_help)));
            v3_help = Translocation(d_x, d_y, d_z).vectorMult(Rotation(alpha, beta, gamma).vectorMult(Scale(c_x, c_y, c_z).vectorMult(v3_help)));*/
            normal1_help = Translocation(d_x, d_y, d_z).vectorMult(Rotation(alpha, beta, gamma).vectorMult(normal1_help));
            normal2_help = Translocation(d_x, d_y, d_z).vectorMult(Rotation(alpha, beta, gamma).vectorMult(normal2_help));
            normal3_help = Translocation(d_x, d_y, d_z).vectorMult(Rotation(alpha, beta, gamma).vectorMult(normal3_help));


            v1 = new V(new double[]{v1_help.arr[0] / v1_help.arr[3], v1_help.arr[1] / v1_help.arr[3], v1_help.arr[2] / v1_help.arr[3]});
            v2 = new V(new double[]{v2_help.arr[0] / v2_help.arr[3], v2_help.arr[1] / v2_help.arr[3], v2_help.arr[2] / v2_help.arr[3]});
            v3 = new V(new double[]{v3_help.arr[0] / v3_help.arr[3], v3_help.arr[1] / v3_help.arr[3], v3_help.arr[2] / v3_help.arr[3]});
            normal1 = new V(new double[]{normal1_help.arr[0] / normal1_help.arr[3], normal1_help.arr[1] / normal1_help.arr[3], normal1_help.arr[2] / normal1_help.arr[3]});
            normal2 = new V(new double[]{normal2_help.arr[0] / normal2_help.arr[3], normal2_help.arr[1] / normal2_help.arr[3], normal2_help.arr[2] / normal2_help.arr[3]});
            normal3 = new V(new double[]{normal3_help.arr[0] / normal3_help.arr[3], normal3_help.arr[1] / normal3_help.arr[3], normal3_help.arr[2] / normal3_help.arr[3]});


            V AB = v2.sum(v1.scalarMult(-1));
            V AC = v3.sum(v1.scalarMult(-1));
            V t = AB.crossProduct(AC);
            t.normalize();
            V l = new V(new double[]{0, 0, -1});
            double check = t.scalarProduct(l);

            double n1 = normal1.scalarProduct(l);
            double n2 = normal2.scalarProduct(l);
            double n3 = normal3.scalarProduct(l);

            if (check >= 0) {
                Triangle(img, z_buffer, v1, v2, v3, n1, n2, n3, texture_coordinates1, texture_coordinates2, texture_coordinates3, texture, AB, AC);
            }
        }
    }


    public static Matrix Projection(double x, double y, double w, double h, double d) {
        return new Matrix(new double[][]{{w / 2, 0, 0, x + w / 2},
                {0, h / 2, 0, y + h / 2},
                {0, 0, d / 2, d / 2},
                {0, 0, 0, 1}});
    }


    public static Matrix Translocation(double d_x, double d_y, double d_z) {
        return new Matrix(new double[][]{{1, 0, 0, d_x},
                {0, 1, 0, d_y},
                {0, 0, 1, d_z},
                {0, 0, 0, 1}});
    }


    public static Matrix Rotation(double alpha, double beta, double gamma) {
        Matrix Rx = new Matrix(new double[][]
                {{1, 0, 0, 0},
                        {0, Math.cos(alpha), -Math.sin(alpha), 0},
                        {0, Math.sin(alpha), Math.cos(alpha), 0},
                        {0, 0, 0, 1}});
        Matrix Ry = new Matrix(new double[][]
                {{Math.cos(beta), 0, Math.sin(beta), 0},
                        {0, 1, 0, 0},
                        {-Math.sin(beta), 0, Math.cos(beta), 0},
                        {0, 0, 0, 1}});
        Matrix Rz = new Matrix(new double[][]
                {{Math.cos(gamma), -Math.sin(gamma), 0, 0},
                        {Math.sin(gamma), Math.cos(gamma), 0, 0},
                        {0, 0, 1, 0},
                        {0, 0, 0, 1}});
        return Rx.Mult((Ry.Mult(Rz)));
    }

    public static Matrix Scale(double c_x, double c_y, double c_z) {
        return new Matrix(new double[][]{{c_x, 0, 0, 0},
                {0, c_y, 0, 0},
                {0, 0, c_z, 0},
                {0, 0, 0, 1}});
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


    public static void Triangle(BufferedImage img, double[][] z_buffer, V v1, V v2, V v3, double n1, double n2, double n3, V texture_coordinates1, V texture_coordinates2, V texture_coordinates3, BufferedImage texture, V AB, V AC) {
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
                V PA = new V(new double[]{x1, y1}).sum(new V(new double[]{i, j}).scalarMult(-1));
                V V = new V(new double[]{AB.arr[0], AC.arr[0], PA.arr[0]}).crossProduct(new V(new double[]{AB.arr[1], AC.arr[1], PA.arr[1]}));

                double u = (V.arr[0] / V.arr[2]);
                double v = (V.arr[1] / V.arr[2]);

                if (u + v <= 1 && u >= 0 && v >= 0) {
                    double n = n1 * (1 - u - v) + n2 * u + n3 * v;
                    n = Math.max(0, n);

                    double z = z1 * (1 - u - v) + z2 * u + z3 * v;

                    double texture_x = (texture_coordinates1.arr[0] * (1 - u - v) + texture_coordinates2.arr[0] * u + texture_coordinates3.arr[0] * v) * texture.getWidth();
                    double texture_y = (1 - (texture_coordinates1.arr[1] * (1 - u - v) + texture_coordinates2.arr[1] * u + texture_coordinates3.arr[1] * v)) * texture.getHeight();


                    if (z <= z_buffer[i][j]) {
                        int rgb = texture.getRGB((int) texture_x, (int) texture_y);
                        Color textureColor = new Color(rgb);
                        int textureColorR = textureColor.getRed();
                        int textureColorG = textureColor.getGreen();
                        int textureColorB = textureColor.getBlue();
                        img.setRGB(i, j, new Color((int) (n * textureColorR), (int) (n * textureColorG), (int) (n * textureColorB)).getRGB());
                        z_buffer[i][j] = z;
                    }
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