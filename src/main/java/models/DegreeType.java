package models;

public enum DegreeType {
    NO_DEGREE("Нет высшего образования"),
    BACHELOR("Бакалавр"),
    MASTER("Магистр"),
    DOCTOR("Доктор");

    private final String russian;

    DegreeType(String russian) {
        this.russian = russian;
    }

    public String getRussian() {
        return russian;
    }
};

