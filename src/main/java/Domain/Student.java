package Domain;

import Repository.Repository;
import Repository.RepositoryException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private List<Pair<Problem, Integer>> problems;

    /**
     * Creates a new Student with the given parameters
     * @param id is the student's ID
     * @param firstName is the student's first name
     * @param lastName is the students's second name
     */
    public Student(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.problems = new ArrayList<Pair<Problem, Integer>>();
    }

    /**
     * Returns the ID of the student
     * @return the ID of the student (int)
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID for the given student
     * @param id is the new ID to be set
     */
    public void setId(int id) {
        this.id = id;
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
        return firstName+" "+lastName+" ("+id+")" + "\n" + "Problems and grades:" + problems;
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
        return getId() == student.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName());
    }

    /**
     * Returns all the problems of the given student, together with the grades
     * @return a list with pairs Problem -> Grade
     */
    public List<Pair<Problem, Integer>> getProblems() {
        return problems;
    }

    /**
     * Adds a problem for the given student with no grade (-1)
     * @param problem is the new problem for the student
     */
    public void assignProblem(Problem problem){
        this.problems.add(new Pair<Problem, Integer>(problem, -1));
    }

    /**
     * Assign a grade to the student for the given problem in parameters
     * @param problem is the problem to be graded
     * @param grade is the grade (0..10)
     * @throws RepositoryException if the student doesn't have this problem already assigned
     */
    public void assignGrade(Problem problem, int grade) throws RepositoryException {
        if(this.problems.stream().map(pair -> pair.getKey()).filter(prob -> prob.getId() == problem.getId())
        .collect(Collectors.toList()).isEmpty()) throw new RepositoryException("The problem was not assigned to this student");
        this.problems = this.problems.stream().map(pair -> {
            if(pair.getKey().equals(problem)) return new Pair<Problem, Integer>(problem, grade);
            else return pair;
        }).collect(Collectors.toList());
    }
}
