package by.gsu.spring.component;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import by.gsu.constants.RoleConstants;
import by.gsu.jpa.service.dao.RoleServiceDAO;

@Component
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private RoleServiceDAO roleService;

    public DatabaseInitializer(final RoleServiceDAO roleService) {
        this.roleService = roleService;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        roleInit();
    }

    private void roleInit() {
        for (RoleConstants role : RoleConstants.values()) {
            if (roleService.getRoleByName(role.name()) == null) {
                roleService.createRole(role.name());
            }
        }
    }

}
