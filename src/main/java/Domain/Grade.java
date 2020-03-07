package Domain;

import java.util.Objects;

public class Grade extends BaseEntity<Integer>{

    private static int id = 0;
    private Student student;
    private Problem problem;
    private int actualGrade;

    /**
     * Creates a new Grade
     * @param student is the student who is graded
     * @param problem the problem which is evaluated
     * @param actualGrade is the grade value
     */
    public Grade(Student student, Problem problem, int actualGrade) {
        this.setId(id);
        id++;
        this.student = student;
        this.problem = problem;
        this.actualGrade = actualGrade;
    }

    /**
     * Returns the student who is graded
     * @return the student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Sets the student who is graded
     * @param student is the Student
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Returns the problem which is evaluated
     * @return the Problem
     */
    public Problem getProblem() {
        return problem;
    }

    /**
     * Sets the problem which is evaluated
     * @param problem is the Problem
     */
    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    /**
     * Returns the grade value
     * @return the Grade
     */
    public int getActualGrade() {
        return actualGrade;
    }

    /**
     * Sets the grade of the student at the problem
     * @param actualGrade is the grade value
     */
    public void setActualGrade(int actualGrade) {
        this.actualGrade = actualGrade;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudent(), getProblem(), getActualGrade());
    }

    /**
     * Checks whether 2 grades are equal
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Grade)) return false;
        Grade grade = (Grade) obj;
        return student.equals(grade.getStudent()) && problem.equals(grade.getProblem());
    }

    @Override
    public String toString() {
        return student + "," + problem + "," + actualGrade;
    }
}
