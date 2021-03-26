package cn.nju.edu.youngstudent2.StudentManager.controller;

import java.io.Console;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import cn.nju.edu.youngstudent2.StudentManager.model.Student;
import cn.nju.edu.youngstudent2.StudentManager.repository.StudentRepository;



@Controller
public class ManagerController {
    @Value("${spring.application.name}")
    String appName;

    private final StudentRepository students;

    public ManagerController(StudentRepository managerService) {
        this.students = managerService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        System.out.println(appName);
        return "home";
    }

    @GetMapping("/students/new")
    public String processCreationForm(@Valid Student stu, BindingResult result) {
        if (result.hasErrors()) {
            return "";
        }
        else {
            this.students.save(stu);
            return "redirect:/students/" + stu.getId();
        }
    }

    @GetMapping("/students/{id}")
    public ModelAndView showStudent(@PathVariable("id") int id){
        ModelAndView mav = new ModelAndView("studentDetails");
        Student stu = this.students.findById(id);
        System.out.println(stu.getName());
        mav.addObject(stu);
        return mav;
    }
}
