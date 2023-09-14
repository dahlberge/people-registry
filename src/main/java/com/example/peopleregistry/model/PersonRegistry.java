package com.example.peopleregistry.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class PersonRegistry {
    private final Map<String, Person> persons;

    public PersonRegistry() {
        persons = new HashMap<>();
    }

    public void addPerson(String personalNumber, String name, int age) {
        if (personalNumber == null || name == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        if (persons.get(personalNumber) != null) {
            throw new IllegalArgumentException("Person already exists");
        }

        Person person = Person.builder()
            .personalNumber(personalNumber)
            .name(name)
            .age(age)
            .build();
        persons.put(personalNumber, person);
    }

    public Person getPerson(String personalNumber) {
        return persons.get(personalNumber);
    }

    public Person deletePerson(String personalNumber) {
        return persons.remove(personalNumber);
    }

    public void setSpouse(String personalNumber1, String personalNumber2) {
        Optional<Person> maybePerson1 = Optional.ofNullable(persons.get(personalNumber1));
        Optional<Person> maybePerson2 = Optional.ofNullable(persons.get(personalNumber2));

        if (maybePerson1.isEmpty() || maybePerson2.isEmpty()) {
            throw new IllegalArgumentException("Person(s) does not exist");
        }

        Person person1 = maybePerson1.get();
        Person person2 = maybePerson2.get();

        if (person1.getAge() < 18  || person2.getAge() < 18) {
            throw new IllegalArgumentException("Person(s) has to be at least 18 to enter spouse relationship");
        }

        if (person1.getSpouse() != null || person2.getSpouse() != null) {
            throw new IllegalArgumentException("Person(s) are not free to enter spouse relationship");
        }

        person1.setSpouse(personalNumber2);
        person2.setSpouse(personalNumber1);

        persons.put(personalNumber1, person1);
        persons.put(personalNumber2, person2);
    }

    public String removeSpouse(String personalNumber) {
        //TODO
        return ""; // Spouses personal number
    }

    public void setChild(String personalNumberParent, String personalNumberChild) {
        Optional<Person> maybeParent = Optional.ofNullable(persons.get(personalNumberParent));
        Optional<Person> maybeChild = Optional.ofNullable(persons.get(personalNumberChild));

        if (maybeParent.isEmpty() || maybeChild.isEmpty()) {
            throw new IllegalArgumentException("Person(s) does not exist");
        }

        Person parent = maybeParent.get();
        parent.addChild(personalNumberChild);
        persons.put(personalNumberParent, parent);
    }

    public List<Person> getChildren(String personalNumber) {
        Optional<Person> maybeParent = Optional.ofNullable(persons.get(personalNumber));

        if (maybeParent.isEmpty()) {
            throw new IllegalArgumentException("Person does not exist");
        }

        Person parent = maybeParent.get();

        List<Person> children = new ArrayList<>();
        for (String personalNumberChild : parent.getChildren()) {
            children.add(persons.get(personalNumberChild));
        }

        return children;
    }
}
