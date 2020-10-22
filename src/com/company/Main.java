package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main extends JFrame {

    static final int w = 1366;
    static final int h = 768;

    static ArrayList<V> vertexes = new ArrayList<>();
    static ArrayList<V> texture_coordinates = new ArrayList<>();
    static ArrayList<V> normals = new ArrayList<>();
    static ArrayList<V[][]> figures = new ArrayList<>();
    static BufferedImage texture = null;

    public static void draw(Graphics2D g) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Render.render(img, figures, texture);
        g.drawImage(img, 0, 0, null);
    }

    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader("file");
        Scanner sc = new Scanner(fr);
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            String a[] = s.split(" ");
            if (a[0].equals("v")) {
                double arr[] = new double[3];
                arr[0] = Double.parseDouble(a[1]);
                arr[1] = Double.parseDouble(a[2]);
                arr[2] = Double.parseDouble(a[3]);

                vertexes.add(new V(arr));
            }
            if (a[0].equals("vt")) {
                double arr[] = new double[2];
                arr[0] = Double.parseDouble(a[1]);
                arr[1] = Double.parseDouble(a[2]);

                texture_coordinates.add(new V(arr));
            }
            if (a[0].equals("vn")) {
                double arr[] = new double[3];
                arr[0] = Double.parseDouble(a[1]);
                arr[1] = Double.parseDouble(a[2]);
                arr[2] = Double.parseDouble(a[3]);

                normals.add(new V(arr));
            }
            if (a[0].equals("f")) {
                V[][] figure = new V[3][3];
                for (int i = 0; i < 3; i++) {
                    String[] b = a[i + 1].split("/");
                    int k1 = Integer.parseInt(b[0]);
                    int k2 = Integer.parseInt(b[1]);
                    int k3 = Integer.parseInt(b[2]);
                    V v = vertexes.get(k1 - 1);
                    V t = texture_coordinates.get(k2 - 1);
                    V n = normals.get(k3 - 1);
                    figure[i][0] = v;
                    figure[i][1] = t;
                    figure[i][2] = n;
                }

                figures.add(figure);
            }
        }


        try {
            texture = ImageIO.read(new File("uaz_med_white_d.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        Main jf = new Main();
        jf.setSize(w, h);
        jf.setUndecorated(false);
        jf.setTitle("Моя супер программа");
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.createBufferStrategy(2);
        while (true) {
            long frameLength = 1000 / 60; //пытаемся работать из рассчета  60 кадров в секунду
            long start = System.currentTimeMillis();
            BufferStrategy bs = jf.getBufferStrategy();
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            g.clearRect(0, 0, jf.getWidth(), jf.getHeight());
            draw(g);

            bs.show();
            g.dispose();

            long end = System.currentTimeMillis();
            long len = end - start;
            if (len < frameLength) {
                Thread.sleep(frameLength - len);
            }
        }

    }

    public void keyTyped(KeyEvent e) {
    }

    //Вызывается когда клавиша отпущена пользователем, обработка события аналогична keyPressed
    public void keyReleased(KeyEvent e) {

    }
}