package by.gsu.spring;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import by.gsu.database.dao.ArtistDAO;
import by.gsu.database.dao.GenreDAO;
import by.gsu.database.dao.RelationDAO;
import by.gsu.database.dao.RoleDAO;
import by.gsu.database.dao.TrackDAO;
import by.gsu.database.dao.UnitDAO;
import by.gsu.database.dao.UserDAO;
import by.gsu.database.editor.ArtistDatabaseEditor;
import by.gsu.database.editor.GenreDatabaseEditor;
import by.gsu.database.editor.RelationDatabaseEditor;
import by.gsu.database.editor.RoleDatabaseEditor;
import by.gsu.database.editor.TrackDatabaseEditor;
import by.gsu.database.editor.UnitDatabaseEditor;
import by.gsu.database.editor.UserDatabaseEditor;

@Configuration
@ComponentScan("by.gsu")
@EnableTransactionManagement
public class SpringConfig extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/");
    }

    @Override
    public void configureViewResolvers(final ViewResolverRegistry registry) {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/html/");
        viewResolver.setSuffix(".html");
        registry.viewResolver(viewResolver);
    }

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/muschart");
        dataSource.setUsername("maks");
        dataSource.setPassword("111");
        return dataSource;
    }

    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(final DataSource dataSource) {
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
        sessionBuilder.scanPackages("by.gsu.model");
        sessionBuilder.addProperties(getHibernateProperties());
        return sessionBuilder.buildSessionFactory();
    }

    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(final SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(
                sessionFactory);
        return transactionManager;
    }

    @Bean(name = "artistDAO")
    public ArtistDAO getArtistDao(final SessionFactory sessionFactory) {
        return new ArtistDatabaseEditor(sessionFactory);
    }

    @Bean(name = "genreDAO")
    public GenreDAO getGenreDao(final SessionFactory sessionFactory) {
        return new GenreDatabaseEditor(sessionFactory);
    }

    @Bean(name = "relationDAO")
    public RelationDAO getRelationDao(final SessionFactory sessionFactory) {
        return new RelationDatabaseEditor(sessionFactory);
    }

    @Bean(name = "roleDAO")
    public RoleDAO getRoleDao(final SessionFactory sessionFactory) {
        return new RoleDatabaseEditor(sessionFactory);
    }

    @Bean(name = "trackDAO")
    public TrackDAO getTrackDao(final SessionFactory sessionFactory) {
        return new TrackDatabaseEditor(sessionFactory);
    }

    @Bean(name = "unitDAO")
    public UnitDAO getUnitDao(final SessionFactory sessionFactory) {
        return new UnitDatabaseEditor(sessionFactory);
    }

    @Bean(name = "userDAO")
    public UserDAO getUserDao(final SessionFactory sessionFactory) {
        return new UserDatabaseEditor(sessionFactory);
    }

    @Bean(name = "multipartResolver")
    public StandardServletMultipartResolver resolver() {
        return new StandardServletMultipartResolver();
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "update");
        return properties;
    }

}
