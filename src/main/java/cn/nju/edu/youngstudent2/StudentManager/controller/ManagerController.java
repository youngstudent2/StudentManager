package cn.nju.edu.youngstudent2.StudentManager.controller;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.nju.edu.youngstudent2.StudentManager.model.Student;
import cn.nju.edu.youngstudent2.StudentManager.repository.StudentRepository;



@Controller
public class ManagerController {
    @Value("${spring.application.name}")
    String appName;

    private static final String VIEWS_STUDENT_CREATE_OR_UPDATE_FORM = "createOrUpdateStudentForm";
    private final StudentRepository students;

    public ManagerController(StudentRepository managerService) {
        this.students = managerService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        return "home";
    }

    @GetMapping("/students/new") 
    public String initCreationForm(Map<String, Object> model) {
        Student student = new Student();
        model.put("student", student);
        return VIEWS_STUDENT_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/students/new")
    public String processCreationForm(@Valid Student stu, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_STUDENT_CREATE_OR_UPDATE_FORM;
        }
        else {
            this.students.save(stu);
            return "redirect:/students/" + stu.getId();
        }
    }

    @GetMapping("/students/{id}/edit") 
    public String initUpdateStudentForm(@PathVariable("id") int id, Model model) {
        Student student = this.students.findById(id);
        model.addAttribute(student);
        return VIEWS_STUDENT_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/students/{id}/edit")
    public String processUpdateStudentForm(@Valid Student student, BindingResult result, @PathVariable("id") int id) {
        if (result.hasErrors()) {
            return VIEWS_STUDENT_CREATE_OR_UPDATE_FORM;
        }
        else {
            student.setId(id);
            this.students.save(student);
            return "redirect:/students/{id}";
        }
    }

    @GetMapping("/students/find") 
    public String initFindForm(Map<String,Object> model) {
        model.put("student", new Student());
        return "findStudents";
    }

   

    @GetMapping("/students/{id}")
    public ModelAndView showStudent(@PathVariable("id") int id){
        ModelAndView mav = new ModelAndView("studentDetails");
        Student stu = this.students.findById(id);
        mav.addObject(stu);
        return mav;
    }

    @GetMapping("/students")
    public String processFindForm(Student student, BindingResult result, Map<String,Object> model) {
        if (student.getName() == null) {
            student.setName("");
        }
        Collection<Student> results = this.students.findByName(student.getName());
        if (results.isEmpty()) {
            result.rejectValue("name", "notFound", "not found");
            return "students/findStudents";
        }
        else if (results.size() == 1) {
            student = results.iterator().next();
            return "redirect:/students/" + student.getId();
        }
        else {
            model.put("selections", results);
            return "studentsList";
        }
        
    }
}
