package by.gsu.database.dao;

import java.util.List;

import by.gsu.entity.AbstractEntity;

public interface RelationDAO {

    <T extends AbstractEntity> List<T> getElementsByCriteria(Class<T> clazz, int sort, boolean order,
            int page);

    <T extends AbstractEntity> List<T> getElementsByCriteria(Class<T> clazz, int sort, String relation,
            long id, boolean order, final int page);

    <T extends AbstractEntity> int getSizeByCriteria(Class<T> clazz, String relation, long id);

}
