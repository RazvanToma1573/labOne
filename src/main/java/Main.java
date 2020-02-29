import Domain.Problem;
import Domain.Student;
import Domain.Validators.ProblemValidator;
import Domain.Validators.Validator;
import Domain.Validators.StudentValidator;
import Repository.Repository;
import Repository.StudentsRepo;
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
        StudentsService studentService = new StudentsService(studentRepository, studentValidator);
        ProblemsService problemService = new ProblemsService(problemRepository, problemValidator);
        Console console = new Console(studentService, problemService);

        console.menu();

    }

}
