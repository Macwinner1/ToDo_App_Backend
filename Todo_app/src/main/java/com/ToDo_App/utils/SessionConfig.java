package com.ToDo_App.utils;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionConfig {

    @Bean
    public ServletContextInitializer Initializer() {
        return servletContext -> {
            servletContext.getSessionCookieConfig().setName("JSESSIONID");
            servletContext.getSessionCookieConfig().setPath("/");
            servletContext.getSessionCookieConfig().setHttpOnly(true);
            servletContext.getSessionCookieConfig().setSecure(false);
        };
    }

}
