package cn.nju.edu.youngstudent2.StudentManager.repository;


import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import cn.nju.edu.youngstudent2.StudentManager.model.Student;

public interface StudentRepository extends Repository<Student, Integer>{
    
    @Query("SELECT stu FROM Student stu WHERE stu.id =:id")
    @Transactional(readOnly = true)
    Student findById(@Param("id") Integer id);

    @Query("SELECT stu FROM Student stu WHERE stu.name LIKE :name%")
    @Transactional(readOnly = true)
    Collection<Student> findByName(@Param("name") String name);
    
    void save(Student stu);
}
