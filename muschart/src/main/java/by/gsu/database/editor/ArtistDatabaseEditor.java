package by.gsu.database.editor;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.transaction.annotation.Transactional;

import by.gsu.constants.ModelStructureConstants;
import by.gsu.database.dao.ArtistDAO;
import by.gsu.model.ArtistModel;
import by.gsu.model.GenreModel;

public class ArtistDatabaseEditor extends DatabaseEditor implements ArtistDAO {

    public ArtistDatabaseEditor() {
        super();
    }

    public ArtistDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional
    public ArtistModel createArtist(final String name, final String photo,
            final List<GenreModel> genres) {
        ArtistModel artist = new ArtistModel(name, photo, genres);
        sessionFactory.getCurrentSession().save(artist);
        return artist;
    }

    @Override
    @Transactional
    public void deleteArtistById(final long id) {
        sessionFactory.getCurrentSession().delete(getArtistById(id));
    }

    @Override
    @Transactional
    public ArtistModel getArtistById(final long id) {
        return (ArtistModel) sessionFactory.getCurrentSession().get(ArtistModel.class, id);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<ArtistModel> getAllArtists() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArtistModel.class);
        criteria.addOrder(Order.asc(ModelStructureConstants.ArtistFields.NAME));
        return criteria.list();
    }

}
