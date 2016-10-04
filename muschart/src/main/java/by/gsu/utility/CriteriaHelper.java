package by.gsu.utility;

import by.gsu.constants.CountElementsConstants.ArtistCountElements;
import by.gsu.constants.CountElementsConstants.GenreCountElements;
import by.gsu.constants.CountElementsConstants.TrackCountElements;
import by.gsu.constants.ModelStructureConstants.AbstractFields;
import by.gsu.constants.ModelStructureConstants.ArtistFields;
import by.gsu.constants.ModelStructureConstants.Entities;
import by.gsu.constants.ModelStructureConstants.GenreFields;
import by.gsu.constants.ModelStructureConstants.RelationFields;
import by.gsu.constants.ModelStructureConstants.TrackFields;
import by.gsu.entity.AbstractEntity;
import by.gsu.entity.ArtistEntity;
import by.gsu.entity.GenreEntity;
import by.gsu.entity.TrackEntity;

public abstract class CriteriaHelper {

    public static <T extends AbstractEntity> int getCountElements(final Class<T> clazz,
            final int page) {
        if (clazz == ArtistEntity.class) {
            if (page == 0) {
                return ArtistCountElements.ARTIST_COUNT_ELEMENTS;
            }
            return ArtistCountElements.ARTIST_FULL_COUNT_ELEMENTS;
        }
        if (clazz == GenreEntity.class) {
            if (page == 0) {
                return GenreCountElements.GENRE_COUNT_ELEMENTS;
            }
            return GenreCountElements.GENRE_FULL_COUNT_ELEMENTS;
        }
        if (clazz == TrackEntity.class) {
            if (page == 0) {
                return TrackCountElements.TRACK_COUNT_ELEMENTS;
            }
            return TrackCountElements.TRACK_FULL_COUNT_ELEMENTS;
        }
        return 0;
    }

    public static String getSearchField(final String relation) {
        switch (relation) {
            case Entities.ARTIST:
                return RelationFields.ARTISTS;
            case Entities.GENRE:
                return RelationFields.GENRES;
            case Entities.TRACK:
                return RelationFields.TRACKS;
            case Entities.USER:
                return RelationFields.USERS;
            default:
                return "";
        }
    }

    public static <T extends AbstractEntity> String getSortField(final Class<T> clazz,
            final int sort) {
        if (clazz == ArtistEntity.class) {
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
        } else if (clazz == GenreEntity.class) {
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
        } else if (clazz == TrackEntity.class) {
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
        return AbstractFields.ID;
    }

}
