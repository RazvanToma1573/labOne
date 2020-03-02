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
        Repository<Integer, Student> studentRepository = new InMemoryRepository<Integer, Student>(studentValidator);

        Validator<Problem> problemValidator = new ProblemValidator();
        Repository<Integer, Problem> problemRepository = new InMemoryRepository<Integer, Problem>(problemValidator);

        Validator<Grade> gradeValidator = new GradeValidator(studentValidator, problemValidator);
        Repository<Integer, Grade> gradeRepository = new InMemoryRepository<Integer, Grade>(gradeValidator);

        StudentsService studentService = new StudentsService(studentRepository, gradeRepository);
        ProblemsService problemService = new ProblemsService(problemRepository);
        Console console = new Console(studentService, problemService);

        console.menu();

    }

}
