package com.app.portfolio.database.DatabaseAndSpringBoot;


import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public abstract class GenericManagerBase<T , ID> implements GenericManager<T, ID> {

    private JpaRepository<T, ID> jpaRepository;

    @Autowired
    public GenericManagerBase(JpaRepository<T, ID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<T> findById(ID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List findAll() {
        return jpaRepository.findAll();
    }


    @Override
    public void delete(ID id) {
        jpaRepository.deleteById(id);
    }
}
