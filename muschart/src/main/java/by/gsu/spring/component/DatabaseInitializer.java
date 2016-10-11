package by.gsu.spring.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import by.gsu.constants.RoleConstants;
import by.gsu.constants.UnitConstants;
import by.gsu.jpa.service.dao.RoleServiceDAO;
import by.gsu.jpa.service.dao.UnitServiceDAO;

@Component
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RoleServiceDAO roleService;
    @Autowired
    private UnitServiceDAO unitService;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        roleInit();
        unitInit();
    }

    private void roleInit() {
        for (RoleConstants role : RoleConstants.values()) {
            if (!roleService.checkRoleName(role.name())) {
                roleService.createRole(role.name());
            }
        }
    }

    private void unitInit() {
        for (UnitConstants unit : UnitConstants.values()) {
            if (!unitService.checkUnitName(unit.toString())) {
                unitService.createUnit(unit.toString());
            }
        }
    }

}
