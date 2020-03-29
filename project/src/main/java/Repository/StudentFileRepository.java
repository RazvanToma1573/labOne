package Repository;

import Domain.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class StudentFileRepository extends FileRepository<Integer, Student> {

    public StudentFileRepository(String filePath) {
        super(filePath);
        this.readFromFile();
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
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }
}
