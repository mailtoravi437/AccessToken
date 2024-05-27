package org.electronic.store.springbootlogin.config;

import org.modelmapper.ModelMapper;

public class ApplicationConfig {
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
