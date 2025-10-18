package com.ToDo_App.utils;

import org.springframework.boot.web.server.Cookie;
import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CookieConfig {
    @Bean
    public CookieSameSiteSupplier cookieSameSiteSupplier() {
         return CookieSameSiteSupplier.of(Cookie.SameSite.LAX);
    }
}
