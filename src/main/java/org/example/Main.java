package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final int CONSUMER_DELAY = 8000;
    public static final int MANUFACTURER_DELAY = 4000;
    public static void main(String[] args) {

        List<String> cars = new ArrayList<>();

        Runnable consumer = () -> {
                for (int i = 0; i < 2; i++) {
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
                        Thread.sleep(CONSUMER_DELAY);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
        Runnable manufacturer = () -> {
            for (int i = 0; i < 10; i++) {
                synchronized (cars) {
//                    try {
//                        Thread.sleep(MANUFACTURER_DELAY);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
                    System.out.println("Novaya mashina priehala v avtosalon");
                    cars.add("[nomer] " + i);
                    cars.notify();
                }
                try {
                    Thread.sleep(MANUFACTURER_DELAY);
                } catch (InterruptedException e) {
                    return;
                }
            }
        };


        new Thread(consumer, "Customer-1").start();
        new Thread(consumer, "Customer-2").start();
        new Thread(consumer, "Customer-3").start();
        new Thread(consumer, "Customer-4").start();
        new Thread(manufacturer).start();


    }
}