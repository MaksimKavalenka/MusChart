package by.gsu.database.queries;

import static by.gsu.constants.QueryConstants.*;

import org.hibernate.Query;
import org.hibernate.Session;

import by.gsu.database.structure.Tables;
import by.gsu.database.structure.columns.ArtistColumns;

public abstract class ArtistDatabaseQueries {

    private static final String INC_RATING = UPDATE + Tables.ARTIST + SET + ArtistColumns.RATING
            + EQUALLY + ArtistColumns.RATING + PLUS + 1 + WHERE + ArtistColumns.ID + PARAM_EQUALLY
            + ArtistColumns.ID;

    private static final String DEC_RATING = UPDATE + Tables.ARTIST + SET + ArtistColumns.RATING
            + EQUALLY + ArtistColumns.RATING + MINUS + 1 + WHERE + ArtistColumns.ID + PARAM_EQUALLY
            + ArtistColumns.ID;

    public static Query incRating(final Session session, final int id) {
        Query query = session.createQuery(INC_RATING);
        return query.setParameter(ArtistColumns.ID.toString(), id);
    }

    public static Query decRating(final Session session, final int id) {
        Query query = session.createQuery(DEC_RATING);
        return query.setParameter(ArtistColumns.ID.toString(), id);
    }

}
