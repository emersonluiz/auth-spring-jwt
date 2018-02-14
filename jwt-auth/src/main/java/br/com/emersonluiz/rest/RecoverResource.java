package br.com.emersonluiz.rest;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.emersonluiz.exception.NotFoundException;
import br.com.emersonluiz.model.User;
import br.com.emersonluiz.service.TokenService;
import br.com.emersonluiz.service.UserService;
import static br.com.emersonluiz.security.SecurityConstants.HEADER_AUTH_TOKEN;

@RestController
@RequestMapping("/recovers")
public class RecoverResource extends ExceptionResource {

    @Inject
    private UserService userService;

    @Inject TokenService tokenService;

    @PostMapping
    public void recover(@RequestBody(required=true) User user) throws NotFoundException {
        userService.recover(user.getUsername());
    }

    @PostMapping("/{token}")
    public ResponseEntity<Object> validRecover(@PathVariable("token") String token) throws NotFoundException {
        String loginToken = tokenService.validRecover(token);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HEADER_AUTH_TOKEN, loginToken);
        httpHeaders.add("Access-Control-Expose-Headers", HEADER_AUTH_TOKEN);
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

}
