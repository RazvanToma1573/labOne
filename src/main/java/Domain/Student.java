package Domain;

import java.util.*;

public class Student extends BaseEntity<Integer>{
    private String firstName;
    private String lastName;

    /**
     * Creates a new Student with the given parameters
     * @param firstName is the student's first name
     * @param lastName is the students's second name
     */
    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Returns the first name of the student
     * @return the first name of the student (String)
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name for the given student
     * @param firstName is the new first name to be set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the student
     * @return the last name of the student (String)
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name for the given student
     * @param lastName is the new last name to be set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Convert student's data to String
     * @return first name + last name + id (String)
     */
    @Override
    public String toString() {
        return firstName + " "+ lastName + " (" + getId() + ") ";
    }

    /**
     * Compare 2 students if they are equal by ID
     * @param o - Student to be compared
     * @return  true if students have the same ID, false - otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getId().equals(student.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName());
    }

}
