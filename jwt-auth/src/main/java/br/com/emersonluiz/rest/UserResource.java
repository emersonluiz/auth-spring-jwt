package br.com.emersonluiz.rest;

import javax.inject.Inject;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.emersonluiz.exception.NotFoundException;
import br.com.emersonluiz.model.User;
import br.com.emersonluiz.repository.UserRepository;
import br.com.emersonluiz.service.UserService;

@RestController
@RequestMapping("/users")
public class UserResource extends ExceptionResource {

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @PutMapping("/{username}")
    public void updatePassword(String username, User user) throws NotFoundException {
        userService.updatePassword(username, user);
    }
}
