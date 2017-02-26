package com.muschart.utility;

import static com.muschart.constants.EntityConstants.Sort;
import static com.muschart.constants.EntityConstants.ElementsCount.*;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public enum JpaHelper {

    ARTIST {

        @Override
        public int getElementsCount(int page) {
            if (page > 0) {
                return ArtistElementsCount.ARTIST_FULL_ELEMENTS_COUNT;
            } else {
                return ArtistElementsCount.ARTIST_ELEMENTS_COUNT;
            }
        }

        @Override
        public int getPagesCount(long elementsCount) {
            return (int) Math.ceil(elementsCount / (double) ArtistElementsCount.ARTIST_FULL_ELEMENTS_COUNT);
        }

        @Override
        public org.springframework.data.domain.Sort getSort(int sort, boolean order) {
            if (order) {
                switch (sort) {
                    case 0:
                        return Sort.Asc.Artist.NAME;
                    case 1:
                        return Sort.Asc.Artist.ID;
                    case 2:
                        return Sort.Asc.Artist.RATING;
                    default:
                        return Sort.Asc.Artist.ID;
                }
            } else {
                switch (sort) {
                    case 0:
                        return Sort.Desc.Artist.NAME;
                    case 1:
                        return Sort.Desc.Artist.ID;
                    case 2:
                        return Sort.Desc.Artist.RATING;
                    default:
                        return Sort.Desc.Artist.ID;
                }
            }
        }

    },

    GENRE {

        @Override
        public int getElementsCount(int page) {
            if (page > 0) {
                return GenreElementsCount.GENRE_FULL_ELEMENTS_COUNT;
            } else {
                return GenreElementsCount.GENRE_ELEMENTS_COUNT;
            }
        }

        @Override
        public int getPagesCount(long elementsCount) {
            return (int) Math.ceil(elementsCount / (double) GenreElementsCount.GENRE_FULL_ELEMENTS_COUNT);
        }

        @Override
        public org.springframework.data.domain.Sort getSort(int sort, boolean order) {
            if (order) {
                switch (sort) {
                    case 0:
                        return Sort.Asc.Genre.NAME;
                    case 1:
                        return Sort.Asc.Genre.ID;
                    case 2:
                        return Sort.Asc.Genre.RATING;
                    default:
                        return Sort.Asc.Genre.ID;
                }
            } else {
                switch (sort) {
                    case 0:
                        return Sort.Desc.Genre.NAME;
                    case 1:
                        return Sort.Desc.Genre.ID;
                    case 2:
                        return Sort.Desc.Genre.RATING;
                    default:
                        return Sort.Desc.Genre.ID;
                }
            }
        }

    },

    TRACK {

        @Override
        public int getElementsCount(int page) {
            if (page > 0) {
                return TrackElementsCount.TRACK_FULL_ELEMENTS_COUNT;
            } else {
                return TrackElementsCount.TRACK_ELEMENTS_COUNT;
            }
        }

        @Override
        public int getPagesCount(long elementsCount) {
            return (int) Math.ceil(elementsCount / (double) TrackElementsCount.TRACK_FULL_ELEMENTS_COUNT);
        }

        @Override
        public org.springframework.data.domain.Sort getSort(int sort, boolean order) {
            if (order) {
                switch (sort) {
                    case 0:
                        return Sort.Asc.Track.NAME;
                    case 1:
                        return Sort.Asc.Track.ID;
                    case 2:
                        return Sort.Asc.Track.RATING;
                    case 3:
                        return Sort.Asc.Track.RELEASE;
                    default:
                        return Sort.Asc.Track.ID;
                }
            } else {
                switch (sort) {
                    case 0:
                        return Sort.Desc.Track.NAME;
                    case 1:
                        return Sort.Desc.Track.ID;
                    case 2:
                        return Sort.Desc.Track.RATING;
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
        public int getElementsCount(int page) {
            if (page > 0) {
                return ArtistElementsCount.ARTIST_FULL_ELEMENTS_COUNT;
            } else {
                return ArtistElementsCount.ARTIST_ELEMENTS_COUNT;
            }
        }

        @Override
        public int getPagesCount(long elementsCount) {
            return (int) Math.ceil(elementsCount / (double) ArtistElementsCount.ARTIST_FULL_ELEMENTS_COUNT);
        }

        @Override
        public org.springframework.data.domain.Sort getSort(int sort, boolean order) {
            if (order) {
                switch (sort) {
                    case 0:
                        return Sort.Asc.TrackArtist.Artist.NAME;
                    case 1:
                        return Sort.Asc.TrackArtist.Artist.ID;
                    case 2:
                        return Sort.Asc.TrackArtist.Artist.RATING;
                    default:
                        return Sort.Asc.TrackArtist.Artist.ID;
                }
            } else {
                switch (sort) {
                    case 0:
                        return Sort.Desc.TrackArtist.Artist.NAME;
                    case 1:
                        return Sort.Desc.TrackArtist.Artist.ID;
                    case 2:
                        return Sort.Desc.TrackArtist.Artist.RATING;
                    default:
                        return Sort.Desc.TrackArtist.Artist.ID;
                }
            }
        }

    },

    TRACK_ARTIST_TRACK {

        @Override
        public int getElementsCount(int page) {
            if (page > 0) {
                return TrackElementsCount.TRACK_FULL_ELEMENTS_COUNT;
            } else {
                return TrackElementsCount.TRACK_ELEMENTS_COUNT;
            }
        }

        @Override
        public int getPagesCount(long elementsCount) {
            return (int) Math.ceil(elementsCount / (double) TrackElementsCount.TRACK_FULL_ELEMENTS_COUNT);
        }

        @Override
        public org.springframework.data.domain.Sort getSort(int sort, boolean order) {
            if (order) {
                switch (sort) {
                    case 0:
                        return Sort.Asc.TrackArtist.Track.NAME;
                    case 1:
                        return Sort.Asc.TrackArtist.Track.ID;
                    case 2:
                        return Sort.Asc.TrackArtist.Track.RATING;
                    case 3:
                        return Sort.Asc.TrackArtist.Track.RELEASE;
                    default:
                        return Sort.Asc.TrackArtist.Track.ID;
                }
            } else {
                switch (sort) {
                    case 0:
                        return Sort.Desc.TrackArtist.Track.NAME;
                    case 1:
                        return Sort.Desc.TrackArtist.Track.ID;
                    case 2:
                        return Sort.Desc.TrackArtist.Track.RATING;
                    case 3:
                        return Sort.Desc.TrackArtist.Track.RELEASE;
                    default:
                        return Sort.Desc.TrackArtist.Track.ID;
                }
            }
        }

    };

    public abstract int getElementsCount(int page);

    public abstract int getPagesCount(long elementsCount);

    public abstract org.springframework.data.domain.Sort getSort(int sort, boolean order);

    public Pageable getPageRequest(int sort, boolean order, int page) {
        int requestPage = (page > 0) ? page - 1 : 0;
        return new PageRequest(requestPage, getElementsCount(page), getSort(sort, order));
    }

}