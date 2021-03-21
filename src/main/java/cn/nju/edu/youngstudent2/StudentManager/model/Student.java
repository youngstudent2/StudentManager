package cn.nju.edu.youngstudent2.StudentManager.model;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

public class Student extends Person {
    @Column(name = "faculty") 
    @NotEmpty
    protected String faculty;

    @Column(name = "stuId")
    @NotEmpty
    protected String stuId;

    public String getFaculty() {
        return this.faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getStuId() {
        return this.stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    } 
}
