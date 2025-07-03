package ir.ums.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Map<String, Object> resourceAccess = (Map<String, Object>) source.getClaims().get("resource_access");
        if (resourceAccess == null || resourceAccess.isEmpty()) {
            return List.of();
        }

        Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get("ums-client-id");
        if (clientAccess == null || clientAccess.isEmpty()) {
            return List.of();
        }

        List<String> roles = (List<String>) clientAccess.get("roles");
        if (roles == null) {
            return List.of();
        }

        return roles.stream()
                .map(roleName -> "ROLE_" + roleName.toLowerCase()) // Spring needs "ROLE_" prefix
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
