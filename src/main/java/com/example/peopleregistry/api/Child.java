package com.example.peopleregistry.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Child {
    private String name;
    private int age;
}
