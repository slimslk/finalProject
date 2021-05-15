package main.database;

import java.sql.ResultSet;

/**
 * Defines general contract for mapping database result set rows to application entities.
 * @param <T>
 */

public interface EntityMapper<T> {
    T mapRow(ResultSet rs);
}
