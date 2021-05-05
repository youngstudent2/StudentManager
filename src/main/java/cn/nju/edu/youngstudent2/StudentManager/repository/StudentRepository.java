package cn.nju.edu.youngstudent2.StudentManager.repository;



import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.nju.edu.youngstudent2.StudentManager.model.Student;
import io.lettuce.core.dynamic.annotation.Param;


public interface StudentRepository extends JpaRepository<Student, Integer>{
    @Query("SELECT stu FROM Student stu WHERE stu.name LIKE :name%")
    Collection<Student> findAllByName(@Param("name") String name);
}
