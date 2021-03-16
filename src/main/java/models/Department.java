package models;

public class Department {
    private int id;
    private String name;
    private Department headDepartment;
    private boolean archived;

    public Department() {
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

    public Department getHeadDepartment() {
        return headDepartment;
    }

    public void setHeadDepartment(Department headDepartment) {
        this.headDepartment = headDepartment;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
