package com.example.peopleregistry;

import com.example.peopleregistry.api.Child;
import com.example.peopleregistry.api.GetOldestChildResponse;
import com.example.peopleregistry.api.GetPersonResponse;
import com.example.peopleregistry.model.Person;
import com.example.peopleregistry.model.PersonRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class PersonService {
    private final PersonRegistry personRegistry;

    public PersonService(PersonRegistry personRegistry) {
        this.personRegistry = personRegistry;
    }

    public void addPerson(String personalNumber, String name, int age) {
        try {
            personRegistry.addPerson(personalNumber, name, age);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void setSpouse(String personalNumber1, String personalNumber2) {
        try {
            personRegistry.setSpouse(personalNumber1, personalNumber2);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void setChild(String personalNumberParent, String personalNumberChild) {
        try {
            personRegistry.setChild(personalNumberParent, personalNumberChild);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public GetPersonResponse getPerson(String personalNumber) {
        Person person = personRegistry.getPerson(personalNumber);
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        String spouse = null;
        if (person.getSpouse() != null) {
            spouse = personRegistry.getPerson(person.getSpouse()).getName();
        }

        List<Child> children = new ArrayList<>();
        if (person.getChildren().size() > 0) {
            for (String personalNumberChild : person.getChildren()) {
                children.add(Child.builder()
                    .name(personRegistry.getPerson(personalNumberChild).getName())
                    .age(personRegistry.getPerson(personalNumberChild).getAge())
                    .build());
            }
        }
        return GetPersonResponse.builder()
            .name(person.getName())
            .age(person.getAge())
            .spouse(spouse)
            .children(children)
            .build();
    }

    public GetOldestChildResponse getOldestChild(String personalNumber) {
        List<Person> children = personRegistry.getChildren(personalNumber);
        if (children.size() == 0) {
            return null;
        }

        Optional<Person> maybeOldestChild = children.stream().max(Comparator.comparing(Person::getAge));

        if (maybeOldestChild.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Person oldestChild = maybeOldestChild.get();

        return GetOldestChildResponse.builder()
            .personalNumber(oldestChild.getPersonalNumber())
            .name(oldestChild.getName())
            .build();
    }
}
