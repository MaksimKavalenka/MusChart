package by.gsu.database.editor;

import static by.gsu.constants.ModelStructureConstants.ArtistFields;

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
        ArtistModel artist = new ArtistModel();
        artist.setName(name);
        artist.setPhoto(photo);
        artist.setGenres(genres);
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
    public List<ArtistModel> getArtistsByCriteria(final int sort, final boolean order,
            final int page) {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(ArtistModel.class, ArtistFields.ID, order, page);
            case 1:
                return super.getElementsByCriteria(ArtistModel.class, ArtistFields.RATING, order,
                        page);
            case 2:
                return super.getElementsByCriteria(ArtistModel.class, ArtistFields.NAME, order,
                        page);
            default:
                return null;
        }
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
