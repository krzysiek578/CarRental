package com.app.portfolio.database.DatabaseAndSpringBoot;

import java.util.List;
import java.util.Optional;

public interface GenericManager<T extends Model<ID>, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    T save(T toSave);
    Optional<T> update(T toUpdate);
    void delete(ID id);
}
