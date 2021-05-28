package ru.geekbrains.domashnee_zadanie;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Road extends Stage {
    private final Lock lock = new ReentrantLock();
    public static CountDownLatch cdl = new CountDownLatch(Main.CARS_COUNT);;

    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }

    @Override
    public void go(Car c) {


        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
            if (this.length == 40) {
                cdl.countDown();
                if (lock.tryLock())
                    System.out.println(c.getName() + " WIN");
            }
        } catch (InterruptedException e ) {
            e.printStackTrace();
        }

    }
}
