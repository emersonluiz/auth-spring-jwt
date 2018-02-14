package br.com.emersonluiz.security;

import br.com.emersonluiz.model.User;
import br.com.emersonluiz.service.TokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static br.com.emersonluiz.security.SecurityConstants.EXPIRATION_TIME;
//import static br.com.emersonluiz.security.SecurityConstants.HEADER_AUTHORIZATION;
import static br.com.emersonluiz.security.SecurityConstants.HEADER_AUTH_TOKEN;
import static br.com.emersonluiz.security.SecurityConstants.SECRET;
//import static br.com.emersonluiz.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private TokenService tokenService;

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        //try {
            String username = "";
            String password = "";
            String auth = req.getHeader("Authorization");
            if(auth != null) {
                String code = auth.replaceAll("Basic ", "");
                String credentials = new String(Base64.getDecoder().decode(code));
                String[] user_pass = credentials.split(":");

                if(user_pass.length > 0) {
                    if(user_pass[0] != null) {
                        username = user_pass[0];
                    }
    
                    if(user_pass[1] != null) {
                        password = user_pass[1];
                    }
                }
            }

            UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());

            return authenticationManager.authenticate(upat);
      // } catch (Exception e) {
            //throw new RuntimeException(e);
      // }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<GrantedAuthority> list = (List) auth.getAuthorities();

        List<String> claims = new ArrayList<String>();
        for (GrantedAuthority item : list) {
            if(item != null) {
                claims.add(item.getAuthority());
            }
        }

        String token = Jwts.builder()
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .claim("role", claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();

        tokenService.save(((User) auth.getPrincipal()).getUsername(), token);

        res.addHeader("Access-Control-Expose-Headers", "x-auth-token");
        res.addHeader(HEADER_AUTH_TOKEN, token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
