package mpp.socket.server.Domain;

import java.util.Objects;

public class Grade extends BaseEntity<Integer>{

    private static int id = 0;
    private int studentId;
    private int problemId;
    private int actualGrade;

    /**
     * Creates a new Grade
     * @param studentId is the student's ID who is graded
     * @param problemId the ID of the problem which is evaluated
     * @param actualGrade is the grade value
     */
    public Grade(int studentId, int problemId, int actualGrade) {
        this.setId(id);
        id++;
        this.studentId = studentId;
        this.problemId = problemId;
        this.actualGrade = actualGrade;
    }

    /**
     * Returns the student who is graded
     * @return the student
     */
    public int getStudent() {
        return studentId;
    }

    /**
     * Sets the student who is graded
     * @param studentId is the Student's ID
     */
    public void setStudent(int studentId) {
        this.studentId = studentId;
    }

    /**
     * Returns the problem which is evaluated
     * @return the Problem
     */
    public int getProblem() {
        return problemId;
    }

    /**
     * Sets the problem which is evaluated
     * @param problemId is the ID of the Problem
     */
    public void setProblem(int problemId) {
        this.problemId = problemId;
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
        return studentId==grade.getStudent() && problemId==grade.getProblem();
    }

    @Override
    public String toString() {
        return studentId + "," + problemId + "," + actualGrade;
    }
}
