package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Render {

    public static void render(BufferedImage img, ArrayList<V[][]> figures) {
       /* for (int i = 0; i < figures.size(); i++) {
            int x = 250;
            int y = 100;
            V v1 = figures.get(i)[0][0];
            V v2 = figures.get(i)[1][0];//
            V v3 = figures.get(i)[2][0];
            renderLine(img, (int) v1.arr[0] + x, (int) v1.arr[1] + y, (int) v2.arr[0] + x, (int) v2.arr[1] + y, Color.BLACK);
            renderLine(img, (int) v2.arr[0] + x, (int) v2.arr[1] + y, (int) v3.arr[0] + x, (int) v3.arr[1] + y, Color.BLACK);
            renderLine(img, (int) v3.arr[0] + x, (int) v3.arr[1] + y, (int) v1.arr[0] + x, (int) v1.arr[1] + y, Color.BLACK);
        }*/
        for (int i = 0; i < figures.size(); i++) {
            int x = 250;
            int y = 100;
            V v1 = figures.get(i)[0][0];
            V v2 = figures.get(i)[1][0];
            V v3 = figures.get(i)[2][0];
            renderTriangle(img, (int)v1.arr[0]+x, (int)v1.arr[1]+y,(int)v2.arr[0]+x, (int)v2.arr[1]+y,(int)v3.arr[0]+x, (int)v3.arr[1]+y, new Color((255*i)/figures.size(),(255*i)/figures.size(),(255*i)/figures.size()));
        }


    }


    public static void renderLine(BufferedImage img, int x1, int y1, int x2, int y2, Color c) {
        if(x1 ==x2 && y1 == y2){
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


    public static void renderPolygon(BufferedImage img, int x, int y, int k, int d) {
        for (int i = 0; i < k; i++) {
            renderTriangle(img, x, y, (int) (d * Math.cos((2 * Math.PI * i) / k) + x), (int) (d * Math.sin((2 * Math.PI * i) / k) + y), (int) (d * Math.cos((2 * Math.PI * (i + 1)) / k) + x), (int) (d * Math.sin((2 * Math.PI * (i + 1)) / k) + y), new Color(255 - 255 * i / k, 255 - 255 * i / k, 255 * i / k));
        }
    }


}