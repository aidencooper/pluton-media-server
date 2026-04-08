package net.aidencooper.pluton.mediaserver.config;

import net.aidencooper.pluton.mediaserver.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/streams/**").addResourceLocations("file:" + Constants.STREAM_PATH);
    }
}
