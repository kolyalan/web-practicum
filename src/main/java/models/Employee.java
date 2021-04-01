package models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Employee {
    private int id = 0;
    private String name;
    private LocalDate birthDate;
    private DegreeType education;
    private LocalDate employmentDate;
    private String address;
    private String phone;
    private Set<Contract> contracts = new HashSet<>();

    public Employee() {
    }

    public Employee(String name, LocalDate birthDate, DegreeType education, String address, String phone) {
        this.name = name;
        this.birthDate = birthDate;
        this.education = education;
        this.employmentDate = LocalDate.now();
        this.address = address;
        this.phone = phone;
    }

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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public DegreeType getEducation() {
        return education;
    }

    public void setEducation(DegreeType education) {
        this.education = education;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
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

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    public Contract getCurrentContract() {
        Contract current = null;
        for (Contract contract : contracts) {
            if (contract.getQuitDate() == null) {
                current = contract;
                break;
            }
        }
        return current;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && Objects.equals(name, employee.name) && Objects.equals(birthDate, employee.birthDate) && education == employee.education && Objects.equals(employmentDate, employee.employmentDate) && Objects.equals(address, employee.address) && Objects.equals(phone, employee.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate, education, employmentDate, address, phone);
    }
}
