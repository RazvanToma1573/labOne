package Repository;

import Domain.Student;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class StudentFileRepository extends FileRepository<Integer, Student> {

    public StudentFileRepository(String filePath) {
        super(filePath);
        this.readFromFile();
    }

    @Override
    public Optional<Student> findOne(Integer integer) {
        return super.findOne(integer);
    }

    @Override
    public Iterable<Student> findAll() {
        return super.findAll();
    }

    @Override
    public Optional<Student> save(Student entity) {
        return super.save(entity);
    }

    @Override
    public Optional<Student> delete(Integer integer) {
        return super.delete(integer);
    }

    @Override
    public Optional<Student> update(Student entity) {
        return super.update(entity);
    }

    @Override
    protected void writeToFile() {
        super.writeToFile();
    }

    private void readFromFile(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(super.filePath));
            bufferedReader.lines().forEach(entry -> {
                Student student = new Student(entry.split(",")[1], entry.split(",")[2]);
                student.setId(Integer.valueOf(entry.split(",")[0]));
                super.save(student);
            });
            bufferedReader.close();
        } catch (FileNotFoundException exception){
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
