package cn.nju.edu.youngstudent2.StudentManager.controller;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.nju.edu.youngstudent2.StudentManager.model.Student;
import cn.nju.edu.youngstudent2.StudentManager.repository.StudentRepository;
import cn.nju.edu.youngstudent2.StudentManager.service.StudentService;



@RestController
public class ManagerController {
    @Value("${spring.application.name}")
    String appName;

    // @Autowired
    // private CacheManager cacheManager;

    private final StudentService studentService;

    @Autowired
    public ManagerController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students") 
    public Collection<Student> studentList() {
        return this.studentService.findAll();
    }

    @PostMapping("/students")
    public Student newStudent(@RequestBody Student newStudent) {
        return this.studentService.saveStudent(newStudent);
    }

    @PutMapping("/students/{id}")
    public Student updateStudent(@RequestBody Student newStudent, @PathVariable("id") int id) {
        newStudent.setId(id);
        this.studentService.saveStudent(newStudent);
        return newStudent;
    }


    @DeleteMapping("/students/{id}")
    public String removeStudent(@PathVariable("id") int id) {
        this.studentService.removeStudent(id);
        return "redirect:/students/";
    }

   
    @GetMapping("/students/{id}")
    public Student showStudent(@PathVariable("id") int id){
        return this.studentService.findStudentById(id);    
    }

    @GetMapping("/students/name={name}")
    public Collection<Student> showStudents(@PathVariable("name") String name) {
        return this.studentService.findStudentsByName(name);
    }
}
