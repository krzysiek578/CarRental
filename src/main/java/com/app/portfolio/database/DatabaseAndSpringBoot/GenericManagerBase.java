package com.app.portfolio.database.DatabaseAndSpringBoot;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public class GenericManagerBase<T extends Model<ID>, ID> implements GenericManager<T, ID> {
    private final JpaRepository<T, ID> jpaRepository;

    public GenericManagerBase(final JpaRepository<T, ID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<T> findById(ID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public T save(T toSave) {
        if (toSave.getId() == null) {
            toSave.setId(null);
            return jpaRepository.save(toSave);
        }
        return jpaRepository.save(toSave);
    }

    @Override
    public Optional<T> update(T toUpdate) {
        return findById(toUpdate.getId())
                .map(obj -> jpaRepository.save(toUpdate));
    }


    @Override
    public void delete(ID id) {
        jpaRepository.deleteById(id);
    }
}
