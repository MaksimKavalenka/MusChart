package by.gsu.bean.entity;

import by.gsu.entity.ArtistEntity;

public class ArtistInfoEntity extends ArtistEntity {

    private static final long serialVersionUID = -4564323966609562265L;

    private boolean           isLiked;

    public ArtistInfoEntity() {
        super();
    }

    public ArtistInfoEntity(final long id, final String name, final String photo, final long rating,
            final boolean isLiked) {
        super(id, name, photo, rating, null, null, null);
        this.isLiked = isLiked;
    }

    public ArtistInfoEntity(final ArtistEntity artist, final boolean isLiked) {
        super(artist.getId(), artist.getName(), artist.getPhoto(), artist.getRating(), null, null,
                null);
        this.isLiked = isLiked;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(final boolean isLiked) {
        this.isLiked = isLiked;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[id:" + super.getId() + ",name:" + super.getName()
                + ",photo:" + super.getPhoto() + ",rating:" + super.getRating() + ",isLiked:"
                + isLiked + "]";
    }

}
