package models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Department {
    private int id;
    private String name;
    private Department headDepartment;
    private boolean archived;
    private Set<Department> childDeps = new HashSet<>();

    public Department() {
    }

    public Department(String name, Department head) {
        this.id = 0;
        this.name = name;
        this.headDepartment = head;
        this.archived = false;
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

    public Set<Department> getChildDeps() {
        return childDeps;
    }

    public Set<Department> getActiveChildDeps() {
        Set<Department> ret = new HashSet<>(childDeps);
        for (Department dep : childDeps) {
            if (dep.isArchived()) {
                ret.remove(dep);
            }
        }
        return ret;
    }

    public void setChildDeps(Set<Department> childDeps) {
        this.childDeps = childDeps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Department other = (Department) o;
        return other.id == id &&
                other.name.equals(name) &&
                other.archived == archived;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, archived);
    }
}
