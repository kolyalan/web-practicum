package models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Position {
    private int id = 0;
    private String name;
    private String responsibility;
    private boolean archived = false;
    private Set<Contract> contracts = new HashSet<>();

    public Position() {

    }

    public Position(String name, String responsibility) {
        this.name = name;
        this.responsibility = responsibility;
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

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> workers) {
        this.contracts = workers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return  id == position.id &&
                archived == position.archived &&
                Objects.equals(name, position.name) &&
                Objects.equals(responsibility, position.responsibility);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, responsibility, archived);
    }
}
