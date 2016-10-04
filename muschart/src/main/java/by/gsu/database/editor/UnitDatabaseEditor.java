package by.gsu.database.editor;

import static by.gsu.constants.ModelStructureConstants.UnitFields;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.transaction.annotation.Transactional;

import by.gsu.database.dao.UnitDAO;
import by.gsu.entity.UnitEntity;

public class UnitDatabaseEditor extends DatabaseEditor implements UnitDAO {

    public UnitDatabaseEditor() {
        super();
    }

    public UnitDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional
    public UnitEntity getUnitById(final long id) {
        return (UnitEntity) sessionFactory.getCurrentSession().get(UnitEntity.class, id);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<UnitEntity> getAllUnits() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UnitEntity.class);
        criteria.addOrder(Order.asc(UnitFields.ID));
        return criteria.list();
    }

}
