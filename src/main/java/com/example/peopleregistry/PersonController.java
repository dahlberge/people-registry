package com.example.peopleregistry;

import com.example.peopleregistry.api.GetOldestChildResponse;
import com.example.peopleregistry.api.GetPersonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("persons")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("{personalNumber}")
    public GetPersonResponse getPerson(@PathVariable String personalNumber) {
        return personService.getPerson(personalNumber);
    }

    @PostMapping
    public void addPerson(@RequestParam String personalNumber, @RequestParam String name, @RequestParam int age) {
        personService.addPerson(personalNumber, name, age);
    }

    @PutMapping("{personalNumber}/spouse/{personalNumberSpouse}")
    public void setSpouse(@PathVariable String personalNumber, @PathVariable String personalNumberSpouse) {
        this.personService.setSpouse(personalNumber, personalNumberSpouse);
    }

    @PutMapping("{personalNumber}/children/{personalNumberChild}")
    public void setChild(@PathVariable String personalNumber, @PathVariable String personalNumberChild) {
        personService.setChild(personalNumber, personalNumberChild);
    }

    @GetMapping("{personalNumber}/children/oldest")
    public GetOldestChildResponse getOldestChild(@PathVariable String personalNumber) {
        GetOldestChildResponse response = personService.getOldestChild(personalNumber);
        if (response == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return response;
    }

}
