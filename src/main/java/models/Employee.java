package models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

enum DegreeType {NO_DEGREE, BACHELOR, MASTER, DOCTOR};

public class Employee {
    private int id;
    private String name;
    private Date birthDate;
    private DegreeType education;
    private Date employmentDate;
    private String address;
    private String phone;
    private Set contracts = new HashSet();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public DegreeType getEducation() {
        return education;
    }

    public void setEducation(DegreeType education) {
        this.education = education;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set getContracts() {
        return contracts;
    }

    public void setContracts(Set contracts) {
        this.contracts = contracts;
    }
}
