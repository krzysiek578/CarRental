package com.app.portfolio.database.DatabaseAndSpringBoot.area;

import com.app.portfolio.database.DatabaseAndSpringBoot.GenericManagerBase;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class AreaManager extends GenericManagerBase<Area, Long> {


    private AreaRepository areaRepository;

    @Autowired
    public AreaManager(JpaRepository<Area, Long> jpaRepository, AreaRepository areaRepository) {
        super(jpaRepository);
        this.areaRepository = areaRepository;
    }

    @Override
    @Transactional
    public Area save(Area toSave) {
        if (toSave.getId() != null) {
            Optional<Area> updatedOptionalArea = update(toSave);
            return updatedOptionalArea.orElse(null);
        }
        return areaRepository.save(toSave);
    }

    @Override
    @Transactional
    public Optional<Area> update(Area area) {
     Optional<Area> optionalAreaDAO = areaRepository.findById(area.getId());
     if (optionalAreaDAO.isPresent()){
         Area areaDAO = optionalAreaDAO.get();
         areaDAO.setName(area.getName());
         areaDAO.setDepartments(area.getDepartments());
         return optionalAreaDAO;
     }
     return Optional.empty();
    }
}
