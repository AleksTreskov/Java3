package ru.geekbrains.domashnee_zadanie;



public class Main {
    static volatile int count = 1;
    static final int num=5;
    static Object o = new Object();
    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (o) {
            for (int i = 0; i < num; i++) {


                    while (count != 1) {
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("A");
                    count=2;
                    o.notifyAll();
                    }
            }
        }).start();
        new Thread(() -> {
            synchronized (o) {

            for (int i = 0; i < num; i++) {
                    while (count != 2) {
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("B");
                    count = 3;
                    o.notifyAll();
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (o) {
            for (int i = 0; i <num; i++) {

                    while (count !=3) {
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("C");
                    count = 1;
                    o.notifyAll();
                }
            }
        }).start();
    }
}
