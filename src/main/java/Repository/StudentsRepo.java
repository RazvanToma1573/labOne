package Repository;

import Domain.Problem;
import Domain.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentsRepo implements Repository<Student> {
    private List<Student> students;

    /**
     * Creates a new Repository with students
     */
    public StudentsRepo() {
        students = new ArrayList<Student>();
    }

    /**
     * Checks if the repo has the given student
     * @param entity is the student to be found
     * @return true if the student is in the repository, false - otherwise
     */
    @Override
    public boolean find (Student entity) {
        return this.students.contains(entity);
    }

    /**
     * Adds a new student to the repository
     * @param entity is the new student to be added
     * @throws RepositoryException if student to be added is already in the repository
     */
    @Override
    public void add(Student entity) throws RepositoryException{
        if (this.find(entity))
            throw new RepositoryException("Student already contained!");
        students.add(entity);
    }

    /**
     * Removes the given student from the repository
     * @param id is the ID of the student to be removed
     * @throws RepositoryException if the student is not in the repository
     */
    @Override
    public void remove(int id) throws RepositoryException{
        if(!this.find(new Student(id,"","")))
            throw new RepositoryException("Student not contained!");
        students.remove(new Student(id, "", ""));
    }

    /**
     * Returns all the students from the repository
     * @return a list with all the students
     */
    @Override
    public List<Student> getAll() { return this.students; }

    /**
     * Returns a student with has ID equal to the parameter
     * @param id is the ID of the student to be found
     * @return  the student with the given ID
     * @throws RepositoryException if the repository doesn't contain a student with the given ID
     */
    @Override
    public Student getById(int id) throws RepositoryException {
        List<Student> student = this.students.stream().filter(stud -> stud.getId() == id)
                .collect(Collectors.toList());
        if(student.isEmpty()) throw new RepositoryException("There is no student with id "+id);
        return student.get(0);
    }

    /**
     * Assigns a problem to the student with the given ID
     * @param studentId is the ID of the student who has the problem
     * @param problem is the problem to be assigned
     * @throws RepositoryException if the student with the given ID is not in the repository
     */
    @Override
    public void assignProblem(int studentId, Problem problem) throws RepositoryException {
        Student student = this.getById(studentId);
        student.assignProblem(problem);
    }

    /**
     * Assigns a grade to the given student for a given problem
     * @param studentId is the student's ID who has the problem
     * @param problem is the problem to be graded
     * @param grade is the grade to be assigned (0..10)
     * @throws RepositoryException if grade is not in 0..10 or if the student with the given
     * ID is not in the repository
     */
    @Override
    public void assignGrade(int studentId, Problem problem, int grade) throws RepositoryException {
        if(grade<0 || grade>10) throw new RepositoryException("Invalid grade");
        this.getById(studentId).assignGrade(problem, grade);
    }
}
