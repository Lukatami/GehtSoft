package org.example;

public class Main {
    public static void main(String[] args) {
        CustomList<String> list = new CustomList<>();
        list.add("Как");
        list.add("это");
        list.add("сложно");
        System.out.println(list.get(1));
        System.out.println("В списке сейчас " + list.size() + " элементов");
        list.set(1,"не");
        System.out.println(list.get(1));
        System.out.println(list.get(0) + " " + list.get(1) + " " + list.get(2));
        System.out.println(list.toString());
        System.out.println(list.contains("нет"));
        System.out.println(list.indexOf("сложно"));
        list.clear();
        System.out.println(list.isEmpty());
    }
}

