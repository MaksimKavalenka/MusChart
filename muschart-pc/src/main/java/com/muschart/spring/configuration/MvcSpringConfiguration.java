package com.muschart.spring.configuration;

import static com.muschart.constants.MultipartConstants.*;
import static com.muschart.constants.UploadConstants.Path.*;
import static com.muschart.constants.UrlConstants.ANY;
import static com.muschart.constants.UrlConstants.Resources.*;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.muschart.constants.UploadConstants.Path;
import com.muschart.constants.UrlConstants.Page;
import com.muschart.constants.UrlConstants.Resources;

@Configuration
@ComponentScan("com.muschart.spring.component")
@EnableJpaRepositories(basePackages = "com.muschart.jpa.repository")
@EnableTransactionManagement
public class MvcSpringConfiguration extends WebMvcConfigurationSupport {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/muschart");
        dataSource.setUsername("maks");
        dataSource.setPassword("111");
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan("com.muschart.entity");
        entityManagerFactoryBean.setJpaProperties(getHibernateProperties());
        return entityManagerFactoryBean;
    }

    @Bean
    public MultipartResolver multipartResolver() throws IOException {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        FileSystemResource fileSystemResource = new FileSystemResource(TEMP);
        resolver.setMaxInMemorySize(MAX_IN_MEMORY_SIZE);
        resolver.setMaxUploadSize(MAX_UPLOAD_SIZE);
        resolver.setMaxUploadSizePerFile(MAX_UPLOAD_SIZE_PER_FILE);
        resolver.setUploadTempDir(fileSystemResource);
        return resolver;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

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

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.show_sql", "false");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.id.new_generator_mappings", "false");
        return properties;
    }

}