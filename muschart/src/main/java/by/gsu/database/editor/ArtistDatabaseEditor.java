package by.gsu.database.editor;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.transaction.annotation.Transactional;

import by.gsu.constants.ModelStructureConstants;
import by.gsu.database.dao.ArtistDAO;
import by.gsu.entity.ArtistEntity;
import by.gsu.entity.GenreEntity;

public class ArtistDatabaseEditor extends DatabaseEditor implements ArtistDAO {

    public ArtistDatabaseEditor() {
        super();
    }

    public ArtistDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional
    public ArtistEntity createArtist(final String name, final String photo,
            final List<GenreEntity> genres) {
        ArtistEntity artist = new ArtistEntity(name, photo, genres);
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
    public ArtistEntity getArtistById(final long id) {
        return (ArtistEntity) sessionFactory.getCurrentSession().get(ArtistEntity.class, id);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<ArtistEntity> getAllArtists() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArtistEntity.class);
        criteria.addOrder(Order.asc(ModelStructureConstants.ArtistFields.NAME));
        return criteria.list();
    }

}
