package by.gsu.database.dao;

import by.gsu.exception.ValidationException;

public interface IDAO extends AutoCloseable {

    @Override
    void close() throws ValidationException;

}
