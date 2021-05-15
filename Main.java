package ru.geekbrains.domashnee_zadanie;


import java.util.*;

public class Main {

    public static void main(String[] args) {
        Integer[] arr1 = {1, 2, 4, 6, 8, 10};
        swap(arr1, 2, 4);
        Float[] arr2 = {1.0f, 3.0f, 4.0f};
        swap(arr2, 0, 2);
        System.out.println(Arrays.toString(arr1));
        System.out.println(Arrays.toString(arr2));
        arrayListCaster(arr1);
        Box<Apple> box1 = new Box<>();
        Box<Orange> box2 = new Box<>();
        Box<Orange> box3 = new Box<>();
        box1.addFruits(new Apple(), 20);
        box2.addFruits(new Orange(), 30);
        box3.addFruits(new Orange(),30);
        System.out.println(box2.compare(box3));
        System.out.println(box2.getWeight());
        box2.pourOver(box3);
        System.out.println(box1.getWeight());
    }

    //    1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
    public static <T> void swap(T[] arr, int x, int y) {
        T z = arr[x];
        arr[x] = arr[y];
        arr[y] = z;
    }

    //    2. Написать метод, который преобразует массив в ArrayList;
    public static <T> void arrayListCaster(T[] arr) {
        ArrayList<T> arr2 = new ArrayList<>(Arrays.asList(arr));
    }


}


