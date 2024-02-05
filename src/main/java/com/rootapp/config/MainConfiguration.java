package com.rootapp.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfiguration {

    // model mapper configuration
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
