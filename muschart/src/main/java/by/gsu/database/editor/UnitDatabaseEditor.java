package by.gsu.database.editor;

import static by.gsu.constants.ModelStructureConstants.UnitFields;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import by.gsu.database.dao.IUnitDAO;
import by.gsu.exception.ValidationException;
import by.gsu.model.Unit;

public class UnitDatabaseEditor extends DatabaseEditor implements IUnitDAO {

    public UnitDatabaseEditor() throws ValidationException {
        super();
    }

    @Override
    public Unit getUnitById(final long id) throws ValidationException {
        return (Unit) session.get(Unit.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Unit> getAllUnits() throws ValidationException {
        Criteria criteria = session.createCriteria(Unit.class);
        criteria.addOrder(Order.asc(UnitFields.ID));
        return criteria.list();
    }

}
