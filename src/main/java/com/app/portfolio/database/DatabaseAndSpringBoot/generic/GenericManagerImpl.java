package com.app.portfolio.database.DatabaseAndSpringBoot.generic;


import com.app.portfolio.database.DatabaseAndSpringBoot.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public class GenericManagerImpl<T extends Model<ID>, ID> implements GenericManager<T, ID> {
    private final JpaRepository<T, ID> repository;

    public GenericManagerImpl(final JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T save(T toSave) {
        if (toSave.getId() != null) {
            toSave.setId(null);
        }
        return repository.save(toSave);
    }

    @Override
    public Optional<T> update(T toUpdate) {
        return findById(toUpdate.getId())
                .map(obj -> repository.save(toUpdate));
    }


    @Override
    public void delete(ID id) {
        if (repository.existsById(id)){
            repository.deleteById(id);
        }
    }
}
