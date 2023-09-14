package com.example.peopleregistry.api;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetPersonResponse {
    private String name;
    private int age;
    private String spouse;
    private List<Child> children;
}
