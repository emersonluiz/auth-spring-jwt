package br.com.emersonluiz.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskResource {

    @GetMapping
    @PreAuthorize("hasAuthority('read:tasks')")
    public String getTasks() {
        return "Test";
    }
}
