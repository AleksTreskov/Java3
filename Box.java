package ru.geekbrains.domashnee_zadanie;

import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit> {
    private List<T> box = new ArrayList<>();


    public void addFruits(T fruit, int x) {
        for (int i = 0; i < x; i++) {
            box.add(fruit);
        }
    }

    public boolean compare(Box<T> box) {
        return Math.abs(this.getWeight() - box.getWeight()) < 0.000001f;

    }

    public void pourOver(Box<T> box) {
            box.box.addAll(this.box);


    }

    public float getWeight() {
        float weight=0.0f;
        if (!box.isEmpty())
            weight = box.get(0).getWeight()*box.size();
        return weight;
    }
}
