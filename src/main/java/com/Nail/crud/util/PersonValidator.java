package com.Nail.crud.util;

import com.Nail.crud.dao.PersonDAO;
import com.Nail.crud.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (personDAO.show(person.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "This email is already taken");
        }
        //смотрим, есть ли человек с таким же email'om в БД

        if (!Character.isUpperCase(person.getName().codePointAt(0)))errors.rejectValue("name", "", "Name chould start with a capital letter");
    }
}
