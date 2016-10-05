package by.gsu.utility;

import static by.gsu.constants.EntityConstants.Sort;

import by.gsu.entity.AbstractEntity;

public abstract class JpaHelper {

    public static <T extends AbstractEntity> org.springframework.data.domain.Sort getArtistSort(
            final int sort, final boolean order) {
        if (order) {
            switch (sort) {
                case 0:
                    return Sort.Asc.Artist.ID;
                case 1:
                    return Sort.Asc.Artist.RATING;
                case 2:
                    return Sort.Asc.Artist.NAME;
                default:
                    return Sort.Asc.Artist.ID;
            }
        } else {
            switch (sort) {
                case 0:
                    return Sort.Desc.Artist.ID;
                case 1:
                    return Sort.Desc.Artist.RATING;
                case 2:
                    return Sort.Desc.Artist.NAME;
                default:
                    return Sort.Desc.Artist.ID;
            }
        }
    }

    public static <T extends AbstractEntity> org.springframework.data.domain.Sort getGenreSort(
            final int sort, final boolean order) {
        if (order) {
            switch (sort) {
                case 0:
                    return Sort.Asc.Genre.ID;
                case 1:
                    return Sort.Asc.Genre.RATING;
                case 2:
                    return Sort.Asc.Genre.NAME;
                default:
                    return Sort.Asc.Genre.ID;
            }
        } else {
            switch (sort) {
                case 0:
                    return Sort.Desc.Genre.ID;
                case 1:
                    return Sort.Desc.Genre.RATING;
                case 2:
                    return Sort.Desc.Genre.NAME;
                default:
                    return Sort.Desc.Genre.ID;
            }
        }
    }

    public static <T extends AbstractEntity> org.springframework.data.domain.Sort getTrackSort(
            final int sort, final boolean order) {
        if (order) {
            switch (sort) {
                case 0:
                    return Sort.Asc.Track.ID;
                case 1:
                    return Sort.Asc.Track.RATING;
                case 2:
                    return Sort.Asc.Track.NAME;
                case 3:
                    return Sort.Asc.Track.RELEASE;
                default:
                    return Sort.Asc.Track.ID;
            }
        } else {
            switch (sort) {
                case 0:
                    return Sort.Desc.Track.ID;
                case 1:
                    return Sort.Desc.Track.RATING;
                case 2:
                    return Sort.Desc.Track.NAME;
                case 3:
                    return Sort.Desc.Track.RELEASE;
                default:
                    return Sort.Desc.Track.ID;
            }
        }
    }

}
