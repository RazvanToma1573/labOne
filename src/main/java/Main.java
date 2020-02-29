import Domain.Problem;
import Domain.Student;
import Domain.Validators.ProblemValidator;
import Domain.Validators.Validator;
import Domain.Validators.StudentValidator;
import Repository.Repository;
import Repository.StudentsRepo;
import Service.Service;
import Service.StudentsService;
import UI.Console;
import Repository.ProblemsRepo;
import Service.ProblemsService;

public class Main {

    public static void main(String[] args){

        Repository<Student> studentRepository = new StudentsRepo();
        Validator<Student> studentValidator = new StudentValidator();
        Repository<Problem> problemRepository = new ProblemsRepo();
        Validator<Problem> problemValidator = new ProblemValidator();
        Service<Student> studentService = new StudentsService<Student>(studentRepository, studentValidator);
        Service<Problem> problemService = new ProblemsService<Problem>(problemRepository, problemValidator);
        Console console = new Console(studentService, problemService);

        console.menu();

    }

}
