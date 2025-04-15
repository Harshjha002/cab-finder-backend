package com.example.cabfinder.handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig class is used to configure static resource handling for the application.
 * In this case, it exposes the uploaded image files located in the local `uploads/` directory
 * so that they can be accessed via URLs starting with `/uploads/**`.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures resource handlers for serving static resources.
     * Maps any request starting with `/uploads/` to files in the local file system directory `uploads/`.
     *
     * Example:
     *   If a file is saved at `uploads/image.jpg`, it can be accessed via:
     *   http://localhost:8080/uploads/image.jpg
     *
     * This is commonly used for serving user-uploaded images or files.
     *
     * @param registry the ResourceHandlerRegistry to configure resource mapping
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}
