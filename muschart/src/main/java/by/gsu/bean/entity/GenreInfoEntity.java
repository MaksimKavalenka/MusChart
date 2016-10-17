package by.gsu.bean.entity;

import by.gsu.entity.GenreEntity;

public class GenreInfoEntity extends GenreEntity {

    private static final long serialVersionUID = 8660168994660325966L;

    private boolean           isLiked;

    public GenreInfoEntity() {
        super();
    }

    public GenreInfoEntity(final long id, final String name, final long rating,
            final boolean isLiked) {
        super(id, name, rating, null, null, null);
        this.isLiked = isLiked;
    }

    public GenreInfoEntity(final GenreEntity genre, final boolean isLiked) {
        super(genre.getId(), genre.getName(), genre.getRating(), null, null, null);
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
                + ",rating:" + super.getRating() + ",isLiked:" + isLiked + "]";
    }

}
