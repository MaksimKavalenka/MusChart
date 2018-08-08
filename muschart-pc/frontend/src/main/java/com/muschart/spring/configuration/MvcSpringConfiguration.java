package com.muschart.spring.configuration;

import static com.muschart.constants.UrlConstants.ANY;
import static com.muschart.constants.UrlConstants.Resources.STATIC_URL;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.muschart.constants.UploadConstants.Path;
import com.muschart.constants.UrlConstants.Page;
import com.muschart.constants.UrlConstants.Resources;

@Configuration
public class MvcSpringConfiguration extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(Page.Resources.WEBJARS_URL + ANY).addResourceLocations(Resources.Type.CLASSPATH + Resources.WEBJARS_URL + "/");
        registry.addResourceHandler(ANY).addResourceLocations(STATIC_URL + "/");
        registry.addResourceHandler(Page.Resources.AUDIO_URL + ANY).addResourceLocations(Resources.Type.FILE + Path.AUDIO_UPLOAD_PATH + "/");
        registry.addResourceHandler(Page.Resources.ARTIST_IMAGE_URL + ANY).addResourceLocations(Resources.Type.FILE + Path.ARTIST_PHOTO_UPLOAD_PATH + "/");
        registry.addResourceHandler(Page.Resources.TRACK_IMAGE_URL + ANY).addResourceLocations(Resources.Type.FILE + Path.TRACK_COVER_UPLOAD_PATH + "/");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix(Page.Resources.HTML_URL + "/");
        viewResolver.setSuffix(".html");
        registry.viewResolver(viewResolver);
    }

}