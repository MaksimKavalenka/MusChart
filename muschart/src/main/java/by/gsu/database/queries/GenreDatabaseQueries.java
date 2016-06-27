package by.gsu.database.queries;

import static by.gsu.constants.QueryConstants.*;

import org.hibernate.Query;
import org.hibernate.Session;

import by.gsu.database.structure.Tables;
import by.gsu.database.structure.columns.GenreColumns;

public abstract class GenreDatabaseQueries {

    private static final String INC_RATING = UPDATE + Tables.GENRE + SET + GenreColumns.RATING
            + EQUALLY + GenreColumns.RATING + PLUS + 1 + WHERE + GenreColumns.ID + PARAM_EQUALLY
            + GenreColumns.ID;

    private static final String DEC_RATING = UPDATE + Tables.GENRE + SET + GenreColumns.RATING
            + EQUALLY + GenreColumns.RATING + MINUS + 1 + WHERE + GenreColumns.ID + PARAM_EQUALLY
            + GenreColumns.ID;

    public static Query incRating(final Session session, final int id) {
        Query query = session.createQuery(INC_RATING);
        return query.setParameter(GenreColumns.ID.toString(), id);
    }

    public static Query decRating(final Session session, final int id) {
        Query query = session.createQuery(DEC_RATING);
        return query.setParameter(GenreColumns.ID.toString(), id);
    }

}
