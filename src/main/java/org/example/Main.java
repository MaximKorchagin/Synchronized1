package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        List<String> cars = new ArrayList<>();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                synchronized (cars) {
                    System.out.println(Thread.currentThread().getName() + " zashel v avtosalon");
                    if (cars.isEmpty()) {
                        try {
                            System.out.println("Mashin net");
                            cars.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    cars.remove(0);
                    System.out.println(Thread.currentThread().getName() + " uehal na novenkom avto");
                }
            }
        }).start();


        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                synchronized (cars) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Novaya mashina priehala v avtosalon");
                    cars.add("[nomer] " + i);
                    cars.notify();
                    //
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();



    }
}