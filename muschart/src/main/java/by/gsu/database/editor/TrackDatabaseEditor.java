package by.gsu.database.editor;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import by.gsu.database.dao.TrackDAO;
import by.gsu.entity.ArtistEntity;
import by.gsu.entity.GenreEntity;
import by.gsu.entity.TrackEntity;
import by.gsu.entity.UnitEntity;

public class TrackDatabaseEditor extends DatabaseEditor implements TrackDAO {

    public TrackDatabaseEditor() {
        super();
    }

    public TrackDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional
    public TrackEntity createTrack(final String name, final String song, final String cover,
            final String video, final Date release, final List<UnitEntity> units,
            final List<ArtistEntity> artists, final List<GenreEntity> genres) {
        TrackEntity track = new TrackEntity(name, song, cover, video, release, units, artists,
                genres);
        sessionFactory.getCurrentSession().save(track);
        return track;
    }

    @Override
    @Transactional
    public void deleteTrackById(final long id) {
        sessionFactory.getCurrentSession().delete(getTrackById(id));
    }

    @Override
    @Transactional
    public TrackEntity getTrackById(final long id) {
        return (TrackEntity) sessionFactory.getCurrentSession().get(TrackEntity.class, id);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<TrackEntity> getAllTracks() {
        return sessionFactory.getCurrentSession().createCriteria(TrackEntity.class).list();
    }

}
