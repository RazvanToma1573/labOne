import Domain.Grade;
import Domain.Problem;
import Domain.Student;
import Domain.Validators.GradeValidator;
import Domain.Validators.ProblemValidator;
import Domain.Validators.Validator;
import Domain.Validators.StudentValidator;
import Repository.Repository;
import Repository.InMemoryRepository;
import Service.StudentsService;
import UI.Console;
import Service.ProblemsService;

public class Main {

    public static void main(String[] args){

        Validator<Student> studentValidator = new StudentValidator();
        Repository<Integer, Student> studentRepository = new InMemoryRepository<Integer, Student>();

        Validator<Problem> problemValidator = new ProblemValidator();
        Repository<Integer, Problem> problemRepository = new InMemoryRepository<Integer, Problem>();

        Validator<Grade> gradeValidator = new GradeValidator();
        Repository<Integer, Grade> gradeRepository = new InMemoryRepository<Integer, Grade>();

        StudentsService studentService = new StudentsService(studentRepository, gradeRepository, studentValidator, gradeValidator, problemValidator);
        ProblemsService problemService = new ProblemsService(problemRepository, problemValidator);
        Console console = new Console(studentService, problemService);

        console.menu();

    }

}
