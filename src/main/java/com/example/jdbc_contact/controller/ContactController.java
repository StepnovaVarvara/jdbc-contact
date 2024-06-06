package com.example.jdbc_contact.controller;

import com.example.jdbc_contact.dto.Contact;
import com.example.jdbc_contact.repository.DBContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ContactController {

    private final DBContactRepository dbContactRepository;

    //TODO Выводить все контакты в таблице. Выводиться должны все поля сущности.
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("contacts", dbContactRepository.findAll());

        return "index";
    }

    //TODO Добавлять новый контакт через форму. ID не должен добавляться через UI.
    @GetMapping("/contact/create")
    public String showCreateForm(Model model) {
        model.addAttribute("contact", new Contact());

        return "create";
    }
    @PostMapping("/contact/create")
    public String createContact(@ModelAttribute Contact contact) {
        dbContactRepository.save(contact);

        return "redirect:/";
    }

    //TODO Редактировать существующие контакты через форму. ID не должен меняться.
    @GetMapping("/contact/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Contact> contact = dbContactRepository.findById(id);
        if (contact != null) {
            model.addAttribute("contact", contact);
            return "edit";
        }

        return "redirect:/";
    }
    @PostMapping("/contact/edit")
    public String editContact(@ModelAttribute Contact contact) {
        dbContactRepository.update(contact);

        return "redirect:/";
    }

    //TODO Удалять конкретный контакт через кнопку в списке контактов.
    @GetMapping("contact/delete/{id}")
    public String deleteContact(@PathVariable Long id) {
        dbContactRepository.deleteById(id);

        return "redirect:/";
    }
}
