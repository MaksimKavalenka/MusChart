package by.gsu.database.editor;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import by.gsu.database.dao.RoleDAO;
import by.gsu.model.RoleModel;

public class RoleDatabaseEditor extends DatabaseEditor implements RoleDAO {

    public RoleDatabaseEditor() {
        super();
    }

    public RoleDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional
    public RoleModel getRoleById(final long id) {
        return (RoleModel) sessionFactory.getCurrentSession().get(RoleModel.class, id);
    }

}
