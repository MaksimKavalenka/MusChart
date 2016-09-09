package by.gsu.database.editor;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import by.gsu.database.dao.TrackDAO;
import by.gsu.model.ArtistModel;
import by.gsu.model.GenreModel;
import by.gsu.model.TrackModel;
import by.gsu.model.UnitModel;

public class TrackDatabaseEditor extends DatabaseEditor implements TrackDAO {

    public TrackDatabaseEditor() {
        super();
    }

    public TrackDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional
    public TrackModel createTrack(final String name, final String song, final String cover,
            final String video, final Date release, final List<UnitModel> units,
            final List<ArtistModel> artists, final List<GenreModel> genres) {
        TrackModel track = new TrackModel(name, song, cover, video, release, units, artists,
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
    public TrackModel getTrackById(final long id) {
        return (TrackModel) sessionFactory.getCurrentSession().get(TrackModel.class, id);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<TrackModel> getAllTracks() {
        return sessionFactory.getCurrentSession().createCriteria(TrackModel.class).list();
    }

}
