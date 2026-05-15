package com.example;


import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean<BookingServlet> bookingServlet(){
        return new ServletRegistrationBean<>(new BookingServlet(), "/booking/*");
    }
}




