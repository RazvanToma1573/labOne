import Domain.Grade;
import Domain.Problem;
import Domain.Student;
import Domain.Validators.GradeValidator;
import Domain.Validators.ProblemValidator;
import Domain.Validators.Validator;
import Domain.Validators.StudentValidator;
import Repository.Repository;
import Service.StudentsService;
import UI.Console;
import Service.ProblemsService;
import Repository.StudentXMLRepository;
import Repository.GradeXMLRepository;
import Repository.ProblemXMLRepository;
import Repository.SortedRepository;
import Repository.GradeDBRepository;

public class Main {

    public static void main(String[] args){

        Validator<Student> studentValidator = new StudentValidator();
        Repository<Integer, Student> studentRepository = new StudentXMLRepository("src/main/java/Repository/Students.xml");

        Validator<Problem> problemValidator = new ProblemValidator();
        Repository<Integer, Problem> problemRepository = new ProblemXMLRepository("src/main/java/Repository/Problems.xml");

        Validator<Grade> gradeValidator = new GradeValidator();
        SortedRepository<Integer, Grade> gradeRepository = new GradeDBRepository();

        ProblemsService problemService = new ProblemsService(problemRepository, problemValidator);
        StudentsService studentService = new StudentsService(studentRepository, gradeRepository, studentValidator, gradeValidator, problemService);
        Console console = new Console(studentService, problemService);

        console.menu();

    }

}
