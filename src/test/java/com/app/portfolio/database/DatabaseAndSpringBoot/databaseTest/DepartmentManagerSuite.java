package com.app.portfolio.database.DatabaseAndSpringBoot.databaseTest;


import com.app.portfolio.database.DatabaseAndSpringBoot.department.Department;
import com.app.portfolio.database.DatabaseAndSpringBoot.department.DepartmentManager;
import com.app.portfolio.database.DatabaseAndSpringBoot.department.DepartmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class DepartmentManagerSuite {
    @InjectMocks
    DepartmentManager departmentManager;

    @Mock
    DepartmentRepository departmentRepository;

    @Test
    public void findAllTest() {
        //Given
        given(departmentRepository.findAll()).willReturn(new ArrayList<>(
                        List.of(
                                new Department("FirstDepartment"),
                                new Department("SecondDepartment"),
                                new Department("ThirdDepartment")
                        )
                )
        );
        //When
        List<Department> departments = departmentManager.findAll();
        //Then
        Assertions.assertEquals(3, departments.size());
        Assertions.assertEquals("SecondDepartment", departments.get(1).getName());
        verify(departmentRepository, times(1)).findAll();
    }


    @Test
    public void findByIdTest() {
        //Given
        given(departmentRepository.findById(2L)).willReturn(Optional.of(new Department("ThirdDepartment")));
        //When
        Optional<Department> department = departmentManager.findById(2L);
        //Then
        Assertions.assertEquals("ThirdDepartment", department.get().getName());
        verify(departmentRepository, times(1)).findById(2L);
    }


    @Test
    public void saveTestWithoutID() {
        //Given
        Department department = new Department("ThirdDepartment");
        given(departmentRepository.save(department)).willReturn(department);
        //When
        Department departmentSaved = departmentManager.save(department);
        //Then
        Assertions.assertEquals("ThirdDepartment", departmentSaved.getName());
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    public void saveTestWithID() {
        //Given
        Department department = new Department(2L, "ThirdDepartment", null, null, null);
        given(departmentRepository.save(department)).willReturn(department);
        //When
        Department departmentSaved = departmentManager.save(department);
        //Then
        Assertions.assertEquals("ThirdDepartment", departmentSaved.getName());
        Assertions.assertNull(departmentSaved.getId()); //null = niceMock
        verify(departmentRepository, times(1)).save(department);
    }


    @Test
    public void updateTest() {
        //Given
        Department department = new Department(2L, "ThirdDepartment", null, null, null);
        given(departmentRepository.findById(2L)).willReturn(Optional.of(department));
        Department changeDepartment = new Department(2L, "Change", null, null, null);
        given(departmentRepository.save(changeDepartment)).willReturn(changeDepartment);
        //When
        Optional<Department> changedDepartment = departmentManager.update(changeDepartment);
        //Then
        Assertions.assertEquals("Change", changedDepartment.get().getName());
        verify(departmentRepository, times(1)).save(changeDepartment);
    }

    @Test
    public void updateNotFoundObjectTest() {
        //Given
        given(departmentRepository.findById(2L)).willReturn(Optional.empty());
        Department changeDepartment = new Department(2L, "Change", null, null, null);
        given(departmentRepository.save(changeDepartment)).willReturn(changeDepartment);
        //When
        Optional<Department> changedDepartment = departmentManager.update(changeDepartment);
        //Then
        Assertions.assertFalse(changedDepartment.isPresent());
        verify(departmentRepository, never()).save(changeDepartment);
    }

    @Test
    public void deleteFindObjectTest() {
        //Given
        given(departmentRepository.existsById(2L)).willReturn(true);
        //When
        departmentManager.delete(2L);
        //
        verify(departmentRepository, times(1)).deleteById(2L);
    }

    @Test
    public void deleteNotFindObjectTest() {
        //Given
        given(departmentRepository.existsById(2L)).willReturn(false);
        //When
        departmentManager.delete(2L);
        //Then
        verify(departmentRepository, never()).deleteById(2L);
    }
}