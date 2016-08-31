package by.gsu.database.editor;

import static by.gsu.constants.ModelStructureConstants.UnitFields;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.transaction.annotation.Transactional;

import by.gsu.database.dao.UnitDAO;
import by.gsu.model.UnitModel;

public class UnitDatabaseEditor extends DatabaseEditor implements UnitDAO {

    public UnitDatabaseEditor() {
        super();
    }

    public UnitDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional
    public UnitModel getUnitById(final long id) {
        return (UnitModel) sessionFactory.getCurrentSession().get(UnitModel.class, id);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<UnitModel> getAllUnits() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UnitModel.class);
        criteria.addOrder(Order.asc(UnitFields.ID));
        return criteria.list();
    }

}
