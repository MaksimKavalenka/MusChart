package by.gsu.database.editor;

import static by.gsu.constants.ModelStructureConstants.TrackFields;

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
    public void createTrack(final String name, final String song, final String cover,
            final String video, final Date release, final List<UnitModel> units,
            final List<ArtistModel> artists, final List<GenreModel> genres) {
        TrackModel track = new TrackModel();
        track.setName(name);
        track.setSong(song);
        track.setCover(cover);
        track.setVideo(video);
        track.setRelease(release);
        track.setUnits(units);
        track.setArtists(artists);
        track.setGenres(genres);
        sessionFactory.getCurrentSession().save(track);
    }

    @Override
    @Transactional
    public TrackModel getTrackById(final long id) {
        return (TrackModel) sessionFactory.getCurrentSession().get(TrackModel.class, id);
    }

    @Override
    @Transactional
    public List<TrackModel> getTracksByCriteria(final int sort, final boolean order,
            final int page) {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(TrackModel.class, TrackFields.ID, order, page);
            case 1:
                return super.getElementsByCriteria(TrackModel.class, TrackFields.RATING, order,
                        page);
            case 2:
                return super.getElementsByCriteria(TrackModel.class, TrackFields.NAME, order, page);
            case 3:
                return super.getElementsByCriteria(TrackModel.class, TrackFields.RELEASE, order,
                        page);
            default:
                return null;
        }
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<TrackModel> getAllTracks() {
        return sessionFactory.getCurrentSession().createCriteria(TrackModel.class).list();
    }

    @Override
    @Transactional
    public void deleteTrackById(final long id) {
        sessionFactory.getCurrentSession().delete(getTrackById(id));
    }

}
