package Domain;

import java.util.Objects;

public class Problem {

    private int id;
    private String description;
    private String difficulty; //easy/medium/hard

    public Problem(int id, String description, String difficulty) {
        this.id = id;
        this.description = description;
        this.difficulty = difficulty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        else if (!(obj instanceof Problem)) return false;
        Problem problem = (Problem) obj;
        return getId() == problem.getId() &&
                Objects.equals(getDescription(), problem.getDescription()) &&
                Objects.equals(getDifficulty(), problem.getDifficulty());
    }

    @Override
    public String toString() {
        return description + " " + difficulty + " " + "(" + id + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription(), getDifficulty());
    }
}
