package Domain;

import java.util.Objects;

public class Grade extends BaseEntity<Integer>{

    private static int id = 0;
    private Student student;
    private Problem problem;
    private int actualGrade;

    public Grade(Student student, Problem problem, int actualGrade) {
        this.setId(id);
        id++;
        this.student = student;
        this.problem = problem;
        this.actualGrade = actualGrade;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public int getActualGrade() {
        return actualGrade;
    }

    public void setActualGrade(int actualGrade) {
        this.actualGrade = actualGrade;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudent(), getProblem(), getActualGrade());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Grade)) return false;
        Grade grade = (Grade) obj;
        return student.equals(grade.getStudent()) && problem.equals(grade.getProblem());
    }

    @Override
    public String toString() {
        return "student: " + student + "\n" + "problem: " + problem + "\n" + "grade: " + actualGrade;
    }
}
