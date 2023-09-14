package com.example.peopleregistry.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Person {
    private String personalNumber;
    private String name;
    private int age;
    private String spouse;
    @Builder.Default
    private List<String> children = new ArrayList<>();

    public void addChild(String personalNumber) {
        children.add(personalNumber);
    }
}
