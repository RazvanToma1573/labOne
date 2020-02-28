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
    public void add(Student entity) {
        students.add(entity);
    }

    @Override
    public void remove(Student entity) {
        students.remove(entity);
    }
}
