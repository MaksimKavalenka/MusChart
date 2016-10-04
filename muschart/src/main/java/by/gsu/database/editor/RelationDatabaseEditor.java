package by.gsu.database.editor;

import static by.gsu.utility.CriteriaHelper.*;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import by.gsu.constants.ModelStructureConstants.AbstractFields;
import by.gsu.database.dao.RelationDAO;
import by.gsu.entity.AbstractEntity;

public class RelationDatabaseEditor extends DatabaseEditor implements RelationDAO {

    public RelationDatabaseEditor() {
    }

    public RelationDatabaseEditor(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public <T extends AbstractEntity> List<T> getElementsByCriteria(final Class<T> clazz,
            final int sort, final boolean order, final int page) {
        return getElements(clazz, getSortField(clazz, sort), order, page);
    }

    @Override
    @Transactional
    public <T extends AbstractEntity> List<T> getElementsByCriteria(final Class<T> clazz,
            final int sort, final String relation, final long id, final boolean order,
            final int page) {
        return getElements(clazz, getSearchField(relation), getSortField(clazz, sort), id, order,
                page);
    }

    @Override
    @Transactional
    public <T extends AbstractEntity> int getSizeByCriteria(final Class<T> clazz,
            final String relation, final long id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.createAlias(getSearchField(relation), "alias");
        criteria.add(Restrictions.eq("alias" + "." + AbstractFields.ID, id));
        return (int) Math.ceil(criteria.list().size() / (double) getCountElements(clazz, 1));
    }

    private <T extends AbstractEntity> List<T> getElements(final Class<T> clazz,
            final String sortProperty, final boolean order, final int page) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return getElements(criteria, clazz, sortProperty, order, page);
    }

    private <T extends AbstractEntity> List<T> getElements(final Class<T> clazz,
            final String searchProperty, final String sortProperty, final long id,
            final boolean order, final int page) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.createAlias(searchProperty, "alias");
        criteria.add(Restrictions.eq("alias" + "." + AbstractFields.ID, id));
        return getElements(criteria, clazz, sortProperty, order, page);
    }

    @SuppressWarnings("unchecked")
    private <T extends AbstractEntity> List<T> getElements(final Criteria criteria,
            final Class<T> clazz, final String property, final boolean order, final int page) {
        int count = getCountElements(clazz, page);
        int fromIndex = count * page - count;
        int toIndex = count * page;

        if (page == 0) {
            fromIndex = 0;
            toIndex = count;
        }
        if (order) {
            criteria.addOrder(Order.asc(property));
        } else {
            criteria.addOrder(Order.desc(property));
        }
        if (criteria.list().size() >= toIndex) {
            return criteria.list().subList(fromIndex, toIndex);
        }
        if (criteria.list().size() > fromIndex) {
            return criteria.list().subList(fromIndex, criteria.list().size());
        }
        return null;
    }

}
