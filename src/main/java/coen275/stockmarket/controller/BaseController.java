package coen275.stockmarket.controller;


import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseController {
    protected static Long getUserId() {
        Long userId = -1L;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)) {
            userId = Long.parseLong(auth.getName());
        }
        return userId;
    }
}
