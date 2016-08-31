package by.gsu.database.editor;

import static by.gsu.constants.CountElementsConstants.*;
import static by.gsu.constants.ModelStructureConstants.ModelFields;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import by.gsu.model.ArtistModel;
import by.gsu.model.GenreModel;
import by.gsu.model.Model;
import by.gsu.model.TrackModel;

public abstract class DatabaseEditor {

    @Autowired
    protected SessionFactory sessionFactory;

    public DatabaseEditor() {
    }

    public DatabaseEditor(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected <T extends Model> List<T> getElementsByCriteria(final Class<T> clazz,
            final String property, final boolean order, final int page) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);
        return getElements(criteria, clazz, property, order, page);
    }

    protected <T extends Model> List<T> getElementsByCriteria(final Class<T> clazz,
            final String searchProperty, final String sortProperty, final long id,
            final boolean order, final int page) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);
        criteria.createAlias(searchProperty, "alias");
        criteria.add(Restrictions.eq("alias" + "." + ModelFields.ID, id));
        return getElements(criteria, clazz, sortProperty, order, page);
    }

    @SuppressWarnings("unchecked")
    private <T extends Model> List<T> getElements(final Criteria criteria, final Class<T> clazz,
            final String property, final boolean order, final int page) {
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

    private <T extends Model> int getCountElements(final Class<T> clazz, final int page) {
        if (clazz == ArtistModel.class) {
            if (page == 0) {
                return ArtistCountElements.ARTIST_COUNT_ELEMENTS;
            }
            return ArtistCountElements.ARTIST_FULL_COUNT_ELEMENTS;
        }
        if (clazz == GenreModel.class) {
            if (page == 0) {
                return GenreCountElements.GENRE_COUNT_ELEMENTS;
            }
            return GenreCountElements.GENRE_FULL_COUNT_ELEMENTS;
        }
        if (clazz == TrackModel.class) {
            if (page == 0) {
                return TrackCountElements.TRACK_COUNT_ELEMENTS;
            }
            return TrackCountElements.TRACK_FULL_COUNT_ELEMENTS;
        }
        return 0;
    }

}
