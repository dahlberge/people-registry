package com.example.peopleregistry.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetOldestChildResponse {
    private String personalNumber; // I have assumed this is supposed to be the personal number of the child
    private String name;
}
