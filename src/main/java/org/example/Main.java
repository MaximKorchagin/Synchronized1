package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<String> cars = new ArrayList<>();

        Runnable consumer = () -> {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + " zashel v avtosalon");
                    if (cars.isEmpty()) System.out.println("Mashin net");
                    synchronized (cars) {
                        if (cars.isEmpty()) {
                            try {
                                //System.out.println("Mashin net");
                                cars.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        cars.remove(0);
                        System.out.println(Thread.currentThread().getName() + " uehal na novenkom avto");
                    }
                    try {
                        Thread.sleep(8000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
        Runnable manufacturer = () -> {
            for (int i = 0; i < 10; i++) {
                synchronized (cars) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Novaya mashina priehala v avtosalon");
                    cars.add("[nomer] " + i);
                    cars.notify();
                }
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    return;
                }
            }
        };


        new Thread(consumer, "Temych").start();
        new Thread(consumer, "Vladik").start();
        new Thread(manufacturer).start();




    }
}