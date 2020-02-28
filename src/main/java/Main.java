import Domain.Student;
import Domain.Validators.Validator;
import Domain.Validators.StudentValidator;
import Repository.Repository;
import Repository.StudentsRepo;
import Service.Service;
import Service.StudentsService;
import UI.Console;

public class Main {

    public static void main(String[] args){

        Repository<Student> studentRepository = new StudentsRepo();
        Validator<Student> studentValidator = new StudentValidator();
        Service<Student> studentService = new StudentsService<Student>(studentRepository, studentValidator);
        Console console = new Console(studentService);

        console.menu();

    }

}
