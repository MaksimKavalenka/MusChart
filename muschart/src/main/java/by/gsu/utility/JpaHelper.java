package by.gsu.utility;

import static by.gsu.constants.EntityConstants.ElementsCount.*;
import static by.gsu.constants.EntityConstants.Sort;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public enum JpaHelper {

    ARTIST {

        @Override
        public int getElementsCount(final int page) {
            if (page > 0) {
                return ArtistElementsCount.ARTIST_FULL_ELEMENTS_COUNT;
            } else {
                return ArtistElementsCount.ARTIST_ELEMENTS_COUNT;
            }
        }

        @Override
        public int getPagesCount(final long elementsCount) {
            return (int) Math
                    .ceil(elementsCount / (double) ArtistElementsCount.ARTIST_FULL_ELEMENTS_COUNT);
        }

        @Override
        public org.springframework.data.domain.Sort getSort(final int sort, final boolean order) {
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

    },

    GENRE {

        @Override
        public int getElementsCount(final int page) {
            if (page > 0) {
                return GenreElementsCount.GENRE_FULL_ELEMENTS_COUNT;
            } else {
                return GenreElementsCount.GENRE_ELEMENTS_COUNT;
            }
        }

        @Override
        public int getPagesCount(final long elementsCount) {
            return (int) Math
                    .ceil(elementsCount / (double) GenreElementsCount.GENRE_FULL_ELEMENTS_COUNT);
        }

        @Override
        public org.springframework.data.domain.Sort getSort(final int sort, final boolean order) {
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

    },

    TRACK {

        @Override
        public int getElementsCount(final int page) {
            if (page > 0) {
                return TrackElementsCount.TRACK_FULL_ELEMENTS_COUNT;
            } else {
                return TrackElementsCount.TRACK_ELEMENTS_COUNT;
            }
        }

        @Override
        public int getPagesCount(final long elementsCount) {
            return (int) Math
                    .ceil(elementsCount / (double) TrackElementsCount.TRACK_FULL_ELEMENTS_COUNT);
        }

        @Override
        public org.springframework.data.domain.Sort getSort(final int sort, final boolean order) {
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

    },

    TRACK_ARTIST_ARTIST {

        @Override
        public int getElementsCount(final int page) {
            if (page > 0) {
                return ArtistElementsCount.ARTIST_FULL_ELEMENTS_COUNT;
            } else {
                return ArtistElementsCount.ARTIST_ELEMENTS_COUNT;
            }
        }

        @Override
        public int getPagesCount(final long elementsCount) {
            return (int) Math
                    .ceil(elementsCount / (double) ArtistElementsCount.ARTIST_FULL_ELEMENTS_COUNT);
        }

        @Override
        public org.springframework.data.domain.Sort getSort(final int sort, final boolean order) {
            if (order) {
                switch (sort) {
                    case 0:
                        return Sort.Asc.TrackArtist.Artist.ID;
                    case 1:
                        return Sort.Asc.TrackArtist.Artist.RATING;
                    case 2:
                        return Sort.Asc.TrackArtist.Artist.NAME;
                    default:
                        return Sort.Asc.TrackArtist.Artist.ID;
                }
            } else {
                switch (sort) {
                    case 0:
                        return Sort.Desc.TrackArtist.Artist.ID;
                    case 1:
                        return Sort.Desc.TrackArtist.Artist.RATING;
                    case 2:
                        return Sort.Desc.TrackArtist.Artist.NAME;
                    default:
                        return Sort.Desc.TrackArtist.Artist.ID;
                }
            }
        }

    },

    TRACK_ARTIST_TRACK {

        @Override
        public int getElementsCount(final int page) {
            if (page > 0) {
                return TrackElementsCount.TRACK_FULL_ELEMENTS_COUNT;
            } else {
                return TrackElementsCount.TRACK_ELEMENTS_COUNT;
            }
        }

        @Override
        public int getPagesCount(final long elementsCount) {
            return (int) Math
                    .ceil(elementsCount / (double) TrackElementsCount.TRACK_FULL_ELEMENTS_COUNT);
        }

        @Override
        public org.springframework.data.domain.Sort getSort(final int sort, final boolean order) {
            if (order) {
                switch (sort) {
                    case 0:
                        return Sort.Asc.TrackArtist.Track.ID;
                    case 1:
                        return Sort.Asc.TrackArtist.Track.RATING;
                    case 2:
                        return Sort.Asc.TrackArtist.Track.NAME;
                    case 3:
                        return Sort.Asc.TrackArtist.Track.RELEASE;
                    default:
                        return Sort.Asc.TrackArtist.Track.ID;
                }
            } else {
                switch (sort) {
                    case 0:
                        return Sort.Desc.TrackArtist.Track.ID;
                    case 1:
                        return Sort.Desc.TrackArtist.Track.RATING;
                    case 2:
                        return Sort.Desc.TrackArtist.Track.NAME;
                    case 3:
                        return Sort.Desc.TrackArtist.Track.RELEASE;
                    default:
                        return Sort.Desc.TrackArtist.Track.ID;
                }
            }
        }

    };

    public abstract int getElementsCount(final int page);

    public abstract int getPagesCount(final long elementsCount);

    public abstract org.springframework.data.domain.Sort getSort(final int sort,
            final boolean order);

    public Pageable getPageRequest(final int sort, final boolean order, final int page) {
        int requestPage = (page > 0) ? page - 1 : 0;
        return new PageRequest(requestPage, getElementsCount(page), getSort(sort, order));
    }

}
