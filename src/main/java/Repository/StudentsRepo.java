package Repository;

import Domain.Student;

import java.util.ArrayList;
import java.util.List;

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
    public void remove(Student entity) throws RepositoryException{
        if (!this.find(entity))
            throw new RepositoryException("Student not contained!");
        students.remove(entity);
    }

    @Override
    public List<Student> getAll() { return this.students; }
}
