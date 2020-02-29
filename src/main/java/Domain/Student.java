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

    public Student(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.problems = new ArrayList<Pair<Problem, Integer>>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return firstName+" "+lastName+" ("+id+")";
    }

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

    public void assignProblem(Problem problem){
        this.problems.add(new Pair<Problem, Integer>(problem, -1));
        //System.out.println(problems);
    }

    public void assignGrade(Problem problem, int grade) throws RepositoryException {
        if(this.problems.stream().map(pair -> pair.getKey()).filter(prob -> prob.getId() == problem.getId())
        .collect(Collectors.toList()).isEmpty()) throw new RepositoryException("The problem was not assigned to this student");
        this.problems = this.problems.stream().map(pair -> {
            if(pair.getKey().equals(problem)) return new Pair<Problem, Integer>(problem, grade);
            else return pair;
        }).collect(Collectors.toList());
        //System.out.println(problems);
    }
}
