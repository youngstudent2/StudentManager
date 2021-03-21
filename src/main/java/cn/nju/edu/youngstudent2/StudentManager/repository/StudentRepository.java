package cn.nju.edu.youngstudent2.StudentManager.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import cn.nju.edu.youngstudent2.StudentManager.model.Student;

public interface StudentRepository extends Repository<Student, Integer>{
    
    @Query("SELECT DISTINCT student FROM Student WHERE student.id =:id")
    @Transactional(readOnly = true)
    Student findById(@Param("id") Integer id);

    void save(Student stu);
}
