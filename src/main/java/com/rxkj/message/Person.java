package com.rxkj.message;

import lombok.Data;

import java.io.Serializable;
@Data
public class Person implements Serializable {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters and setters
}

