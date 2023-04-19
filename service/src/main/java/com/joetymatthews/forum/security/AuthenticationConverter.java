package com.joetymatthews.forum.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    private final String SCOPE_AUTHORITY_PREFIX = "SCOPE_";
    private final List<String> WELL_KNOWN_SCOPE_ATTRIBUTE_NAME = List.of("scope", "scp", "authorities", "permissions");

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt source) {
        Collection<SimpleGrantedAuthority> authorities = getScopes(source)
                .stream()
                .map(authority -> SCOPE_AUTHORITY_PREFIX + authority)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return Mono.just(new JwtAuthenticationToken(source, authorities));
    }

    private Collection<String> getScopes(Jwt source) {
        List<String> authorities = new ArrayList<>();
        for (String attribute : WELL_KNOWN_SCOPE_ATTRIBUTE_NAME) {
            Object scopes = source.getClaim(attribute);
            if (scopes.getClass() == String.class) {
                String str = (String) scopes;
                if (StringUtils.hasText(str)) authorities.addAll(Arrays.asList(str.split(" ")));
            }
        }

        return authorities;
    }
}
