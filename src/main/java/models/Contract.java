package models;

import java.time.LocalDate;

public class Contract {
    private int id = 0;
    private Employee employee;
    private Department department;
    private Position position;
    private LocalDate employmentDate;
    private LocalDate quitDate;

    public Contract() {
    }

    public Contract(Employee employee, Department department, Position position, LocalDate employmentDate, LocalDate quitDate) {
        this.employee = employee;
        this.department = department;
        this.position = position;
        this.employmentDate = employmentDate;
        this.quitDate = quitDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public LocalDate getQuitDate() {
        return quitDate;
    }

    public void setQuitDate(LocalDate quitDate) {
        this.quitDate = quitDate;
    }
}
