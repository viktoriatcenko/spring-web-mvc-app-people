package ru.maxima.controllers;

// Person - 1 человек
// People - много людей

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maxima.PeopleService;
import ru.maxima.dao.PersonDAO;
import ru.maxima.models.Person;
import ru.maxima.repositories.PeopleRepository;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    // CRUD
    // Create - создать - ! POST - request
    // Read - прочитать - ! GET - request
    // Update - обновить - PUT/PATCH - request
    // Delete - удалить - DELETE - request


    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }


    @GetMapping()
    public String showAllPeople(Model model) {
        List<Person> allPeople = peopleService.getAllPeople();

        model.addAttribute("allPeople", allPeople);

        return "people/view-with-all-people";
    }


    @GetMapping("/{id}")
    public String showPersonById(@PathVariable("id") Integer id, Model model) {
        Person person = peopleService.findById(Long.valueOf(id));
        model.addAttribute("personById", person);
        return "people/view-with-person-by-id";
    }

    @GetMapping("/create")
    public String getPageToCreateNewPerson(Model model) {
        model.addAttribute("newPerson", new Person());
        return "people/view-to-create-new-person";
    }

    @PostMapping()
    public String createNewPerson(@ModelAttribute("newPerson") @Valid Person person,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/view-to-create-new-person";
        }

        peopleService.save(person);

        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String getPageToEditPerson(Model model, @PathVariable("id") Integer id) {
//        model.addAttribute("editedPerson", personDAO.findById(Long.valueOf(id)));
        return "people/view-to-edit-person";
    }

    @PostMapping("/{id}")
    public String editPerson(@PathVariable("id") Integer id,
                             @ModelAttribute("editedPerson") @Valid Person person,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "people/view-to-edit-person";
        }
        peopleService.update(person, id);

        return "redirect:/people";
    }

    @PostMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") Integer id) {
        peopleService.delete(id);

        return "redirect:/people";
    }

    @GetMapping("/makeAdmin")
    public String getPageToMakeAdmin(Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("allPeople", peopleService.getAllPeople());
        return "people/view-to-make-admin";
    }

    @PostMapping("/admin-add")
    public String makeAdmin(@ModelAttribute("person") Person person) {
//        Person byId = peopleService.findById(person.getId());
//        System.out.println("This person is now admin " + byId.getName());
        return "redirect:/people";
    }
}
