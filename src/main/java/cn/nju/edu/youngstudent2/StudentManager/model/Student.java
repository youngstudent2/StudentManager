package cn.nju.edu.youngstudent2.StudentManager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "students")
public class Student extends Person {
    @Column(name = "faculty") 
    @NotEmpty
    protected String faculty;


    public String getFaculty() {
        return this.faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

}
