package Domain;

import java.util.Objects;

public class Problem {

    private int id;
    private String description;
    private String difficulty; //easy/medium/hard

    /**
     * Creates a new problem with the given parameters.
     * @param id is the problem's id.
     * @param description is the problem's description.
     * @param difficulty is the problem's difficulty.
     */
    public Problem(int id, String description, String difficulty) {
        this.id = id;
        this.description = description;
        this.difficulty = difficulty;
    }

    /**
     * Returns the problem's ID.
     * @return the id of the problem (int)
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id of the given problem.
     * @param id is the new id of the problem (int)
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the problem's description.
     * @return the description of the problem (String)
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the given problem.
     * @param description is the new description of the problem (String)
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the problem's difficulty.
     * @return the difficulty of the problem (String)
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Set the difficulty of the given problem.
     * @param difficulty is the new difficulty of the problem (String)
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Compare 2 problems by their id.
     * @param obj problem to be compared
     * @return true if the problems have the same id, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        else if (!(obj instanceof Problem)) return false;
        Problem problem = (Problem) obj;
        return getId() == problem.getId();
    }

    /**
     * Convert problem's data to String
     * @return description + difficulty + id (String)
     */
    @Override
    public String toString() {
        return description + " " + difficulty + " " + "(" + id + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription(), getDifficulty());
    }
}
