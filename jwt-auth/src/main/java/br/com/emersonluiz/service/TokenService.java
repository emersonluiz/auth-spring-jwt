package br.com.emersonluiz.service;

import static br.com.emersonluiz.security.SecurityConstants.EXPIRATION_TIME;
import static br.com.emersonluiz.security.SecurityConstants.SECRET;
import static br.com.emersonluiz.security.SecurityConstants.SECRET_RECOVER;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.emersonluiz.exception.NotFoundException;
import br.com.emersonluiz.model.Token;
import br.com.emersonluiz.model.User;
import br.com.emersonluiz.repository.TokenRepository;
import br.com.emersonluiz.repository.UserRepository;

@Named
public class TokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);

    @Inject
    TokenRepository tokenRepository;

    @Inject
    UserRepository userRepository;

    public Token create(User user, String key) {
        try {
            //token:
            Token token = new Token();
            token.setUser(user);
            token.setNumber(key);
            token.setCreatedDate(new Date());
            token.setEnabled(true);
            tokenRepository.save(token);
            LOGGER.debug("Token created successfully.");
            return token;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    public void save(String username, String token) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = userRepository.findByEmail(username);
            if (user == null) {
                throw new UsernameNotFoundException(username);
            }
        }
        create(user, token);
    }

    public User getUserByToken(String token) throws NotFoundException {
        Token t = tokenRepository.findByNumber(token);
        if(t == null) {
            throw new NotFoundException("Token not found");
        }
        return t.getUser();
    }

    public String validRecover(String token) throws NotFoundException {
        try {
            String t = new String(Base64.getUrlDecoder().decode(token));

            String username = Jwts.parser()
                    .setSigningKey(SECRET_RECOVER.getBytes())
                    .parseClaimsJws(t)
                    .getBody()
                    .getSubject();

            List<String> grants = new ArrayList<String>();
            grants.add("read:tasks");

            String loginToken = Jwts.builder()
                    .setSubject(username)
                    .claim("role", grants)
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                    .compact();

            save(username, loginToken);

            return loginToken;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new NotFoundException(e.getMessage());
        }
    }
}
