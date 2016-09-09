package by.gsu.utility;

import by.gsu.constants.CountElementsConstants.ArtistCountElements;
import by.gsu.constants.CountElementsConstants.GenreCountElements;
import by.gsu.constants.CountElementsConstants.TrackCountElements;
import by.gsu.constants.ModelStructureConstants.ArtistFields;
import by.gsu.constants.ModelStructureConstants.GenreFields;
import by.gsu.constants.ModelStructureConstants.ModelFields;
import by.gsu.constants.ModelStructureConstants.Models;
import by.gsu.constants.ModelStructureConstants.RelationFields;
import by.gsu.constants.ModelStructureConstants.TrackFields;
import by.gsu.model.ArtistModel;
import by.gsu.model.GenreModel;
import by.gsu.model.Model;
import by.gsu.model.TrackModel;

public abstract class CriteriaHelper {

    public static <T extends Model> int getCountElements(final Class<T> clazz, final int page) {
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

    public static String getSearchField(final String relation) {
        switch (relation) {
            case Models.ARTIST:
                return RelationFields.ARTISTS;
            case Models.GENRE:
                return RelationFields.GENRES;
            case Models.TRACK:
                return RelationFields.TRACKS;
            case Models.USER:
                return RelationFields.USERS;
            default:
                return "";
        }
    }

    public static <T extends Model> String getSortField(final Class<T> clazz, final int sort) {
        if (clazz == ArtistModel.class) {
            switch (sort) {
                case 0:
                    return ArtistFields.ID;
                case 1:
                    return ArtistFields.RATING;
                case 2:
                    return ArtistFields.NAME;
                default:
                    return ArtistFields.ID;
            }
        } else if (clazz == GenreModel.class) {
            switch (sort) {
                case 0:
                    return GenreFields.ID;
                case 1:
                    return GenreFields.RATING;
                case 2:
                    return GenreFields.NAME;
                default:
                    return GenreFields.ID;
            }
        } else if (clazz == TrackModel.class) {
            switch (sort) {
                case 0:
                    return TrackFields.ID;
                case 1:
                    return TrackFields.RATING;
                case 2:
                    return TrackFields.NAME;
                case 3:
                    return TrackFields.RELEASE;
                default:
                    return TrackFields.ID;
            }
        }
        return ModelFields.ID;
    }

}
