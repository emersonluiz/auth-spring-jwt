package br.com.emersonluiz.rest;

import static br.com.emersonluiz.security.SecurityConstants.HEADER_AUTHORIZATION;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.emersonluiz.exception.NotFoundException;
import br.com.emersonluiz.model.User;
import br.com.emersonluiz.service.TokenService;

@RestController
@RequestMapping("/tokens")
public class TokenResource extends ExceptionResource {

    @Inject
    private TokenService tokenService;

    @GetMapping
    @ResponseBody
    public User findUserByToken(HttpServletRequest request) throws NotFoundException {
        String token = request.getHeader(HEADER_AUTHORIZATION);
        token = token.replaceAll("Bearer ", "");

        return this.tokenService.getUserByToken(token);
    }
}
