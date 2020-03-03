package Repository;

import Domain.Student;
import Domain.Validators.StudentValidator;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

public class InMemoryRepositoryTest {

    @Test
    public void findOne() {
        Validator<Student> studentValidator = new StudentValidator();
        Repository<Integer, Student> studentRepository = new InMemoryRepository<Integer, Student>(studentValidator);

        Student student = new Student("Razvan","Toma");
        student.setId(1);

        try{
            studentRepository.save(student);
        } catch(ValidatorException exception) {
            System.out.println("ValidatorException:" + exception.getMessage());
        } catch(IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }

        assertTrue(1 == studentRepository.findOne(1).get().getId());
    }

    @Test
    public void findAll() {
        Validator<Student> studentValidator = new StudentValidator();
        Repository<Integer, Student> studentRepository = new InMemoryRepository<Integer, Student>(studentValidator);

        Student student1 = new Student("aaaa","bbbb");
        student1.setId(1);

        Student student2 = new Student("ccccc","dddd");
        student2.setId(2);

        try{
            studentRepository.save(student1);
            studentRepository.save(student2);
        } catch(ValidatorException exception) {
            System.out.println("ValidatorException:" + exception.getMessage());
        } catch(IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }

        ArrayList<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(student -> students.add(student));
        assertTrue(2 == students.size());
    }

    @Test
    public void save() {
        Validator<Student> studentValidator = new StudentValidator();
        Repository<Integer, Student> studentRepository = new InMemoryRepository<Integer, Student>(studentValidator);

        Student student1 = new Student("aaaa","bbbb");
        student1.setId(1);


        try{
            studentRepository.save(student1);
        } catch(ValidatorException exception) {
            System.out.println("ValidatorException:" + exception.getMessage());
        } catch(IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }

        assertTrue("aaaa".equals(studentRepository.findOne(1).get().getFirstName()));


        Student student2 = new Student("bbbb","cccc");
        student2.setId(1);


        try{
            studentRepository.save(student2);
        } catch(ValidatorException exception) {
            System.out.println("ValidatorException:" + exception.getMessage());
        } catch(IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }

        assertTrue("aaaa".equals(studentRepository.findOne(1).get().getFirstName()));

    }

    @Test
    public void delete() {
        Validator<Student> studentValidator = new StudentValidator();
        Repository<Integer, Student> studentRepository = new InMemoryRepository<Integer, Student>(studentValidator);

        Student student1 = new Student("aaaa","bbbb");
        student1.setId(1);


        try{
            studentRepository.save(student1);
        } catch(ValidatorException exception) {
            System.out.println("ValidatorException:" + exception.getMessage());
        } catch(IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }


        try{
            studentRepository.delete(1);
        } catch(IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }

        Optional<Student> student = studentRepository.findOne(1);
        assertTrue(!student.isPresent());
    }

    @Test
    public void update() {
        Validator<Student> studentValidator = new StudentValidator();
        Repository<Integer, Student> studentRepository = new InMemoryRepository<Integer, Student>(studentValidator);

        Student student1 = new Student("aaaa","bbbb");
        student1.setId(1);


        try{
            studentRepository.save(student1);
        } catch(ValidatorException exception) {
            System.out.println("ValidatorException:" + exception.getMessage());
        } catch(IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }

        Student student2 = new Student("xxxx","zzzz");
        student2.setId(1);

        try{
            studentRepository.update(student2);
        } catch(ValidatorException exception) {
            System.out.println("ValidatorException:" + exception.getMessage());
        } catch(IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }

        assertTrue("xxxx".equals(studentRepository.findOne(1).get().getFirstName()));
    }
}