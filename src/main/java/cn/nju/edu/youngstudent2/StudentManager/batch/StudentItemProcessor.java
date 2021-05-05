package cn.nju.edu.youngstudent2.StudentManager.batch;

import org.springframework.batch.item.ItemProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.nju.edu.youngstudent2.StudentManager.model.Student;

class StudentItemProcessor implements ItemProcessor<Student, Student> {
    private static final Logger log = LoggerFactory.getLogger(StudentItemProcessor.class);
    @Override
    public Student process(Student student) throws Exception {
        log.info("converting ("+student+") into ("+student+")");
        return student;
    }
    
}