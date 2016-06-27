package by.gsu.database.queries;

import static by.gsu.constants.QueryConstants.*;

import org.hibernate.Query;
import org.hibernate.Session;

import by.gsu.database.structure.Tables;
import by.gsu.database.structure.columns.TrackColumns;

public abstract class TrackDatabaseQueries {

    private static final String INC_RATING = UPDATE + Tables.TRACK + SET + TrackColumns.RATING
            + EQUALLY + TrackColumns.RATING + PLUS + 1 + WHERE + TrackColumns.ID + PARAM_EQUALLY
            + TrackColumns.ID;

    private static final String DEC_RATING = UPDATE + Tables.TRACK + SET + TrackColumns.RATING
            + EQUALLY + TrackColumns.RATING + MINUS + 1 + WHERE + TrackColumns.ID + PARAM_EQUALLY
            + TrackColumns.ID;

    public static Query incRating(final Session session, final int id) {
        Query query = session.createQuery(INC_RATING);
        return query.setParameter(TrackColumns.ID.toString(), id);
    }

    public static Query decRating(final Session session, final int id) {
        Query query = session.createQuery(DEC_RATING);
        return query.setParameter(TrackColumns.ID.toString(), id);
    }

}
