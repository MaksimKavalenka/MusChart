package by.gsu.database.dao;

import java.util.List;

import by.gsu.model.Model;

public interface RelationDAO {

    <T extends Model> List<T> getElementsByCriteria(Class<T> clazz, int sort, boolean order,
            int page);

    <T extends Model> List<T> getElementsByCriteria(Class<T> clazz, int sort, String relation,
            long id, boolean order, final int page);

    <T extends Model> int getSizeByCriteria(Class<T> clazz, String relation, long id);

}
