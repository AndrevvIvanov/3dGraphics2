package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
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

public class Main extends JFrame implements KeyListener {

    static final int w = 1366;
    static final int h = 768;

    static ArrayList<V> vertexes = new ArrayList<>();
    static ArrayList<V> texture_coordinates = new ArrayList<>();
    static ArrayList<V> normals = new ArrayList<>();
    static ArrayList<V[][]> figures = new ArrayList<>();
    static double d_x = 0;
    static double d_y = 0;
    static double alpha = 0;
    static double beta = 0;
    static double gamma = 0;
    static double c_x = 1;
    static double c_y = 1;
    static double c_z = 1;

    static BufferedImage texture = null;
    static double[][] z_buffer = new double[Main.w][Main.h];


    public static void draw(Graphics2D g) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Render.render(img, figures, texture, d_x, d_y, 0, alpha, beta, gamma, c_x, c_y, c_z, z_buffer);
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

        for (int i = 0; i < z_buffer.length; i++) {
            for (int j = 0; j < z_buffer[0].length; j++) {
                z_buffer[i][j] = Integer.MAX_VALUE;
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
        jf.addKeyListener(jf);

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

            for (int i = 0; i < z_buffer.length; i++) {
                for (int j = 0; j < z_buffer[0].length; j++) {
                    z_buffer[i][j] = Integer.MAX_VALUE;
                }
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            alpha = alpha - 15 * (Math.PI / 180);
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            alpha = alpha + 15 * (Math.PI / 180);
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            beta = beta - 15 * (Math.PI / 180);
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            beta = beta + 15 * (Math.PI / 180);
        }
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            gamma = gamma - 15 * (Math.PI / 180);
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
            gamma = gamma + 15 * (Math.PI / 180);
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            d_x = d_x + 10;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            d_x = d_x - 10;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            d_y = d_y - 10;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            d_y = d_y + 10;
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            c_x = c_x * 0.9;
        }
        if (e.getKeyCode() == KeyEvent.VK_T) {
            c_x = c_x / 0.9;
        }
        if (e.getKeyCode() == KeyEvent.VK_F) {
            c_y = c_y * 0.9;
        }
        if (e.getKeyCode() == KeyEvent.VK_G) {
            c_y = c_y / 0.9;
        }
        /*if (e.getKeyCode() == KeyEvent.VK_N) {
            c_z = c_z * 0.9;
        }
        if (e.getKeyCode() == KeyEvent.VK_M) {
            c_z = c_z / 0.9;
        }*/


    }

    public void keyTyped(KeyEvent e) {
    }

    //Вызывается когда клавиша отпущена пользователем, обработка события аналогична keyPressed
    public void keyReleased(KeyEvent e) {
    }
}