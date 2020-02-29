package Repository;

import Domain.Problem;
import Domain.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentsRepo implements Repository<Student> {
    private List<Student> students;

    public StudentsRepo() {
        students = new ArrayList<Student>();
    }

    @Override
    public boolean find (Student entity) {
        return this.students.contains(entity);
    }

    @Override
    public void add(Student entity) throws RepositoryException{
        if (this.find(entity))
            throw new RepositoryException("Student already contained!");
        students.add(entity);
    }

    @Override
    public void remove(int id) throws RepositoryException{
        if(!this.find(new Student(id,"","")))
            throw new RepositoryException("Student not contained!");
        students.remove(new Student(id, "", ""));
    }

    @Override
    public List<Student> getAll() { return this.students; }

    @Override
    public Student getById(int id) throws RepositoryException {
        List<Student> student = this.students.stream().filter(stud -> stud.getId() == id)
                .collect(Collectors.toList());
        if(student.isEmpty()) throw new RepositoryException("There is no student with id "+id);
        return student.get(0);
    }

    @Override
    public void assignProblem(int studentId, Problem problem) throws RepositoryException {
        Student student = this.getById(studentId);
        student.assignProblem(problem);
    }

    @Override
    public void assignGrade(int studentId, Problem problem, int grade) throws RepositoryException {
        if(grade<0 || grade>10) throw new RepositoryException("Invalid grade");
        this.getById(studentId).assignGrade(problem, grade);
    }
}
