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
import Repository.StudentFileRepository;
import Repository.ProblemFileRepository;
import Repository.GradeFileRepository;

public class Main {

    public static void main(String[] args){

        Validator<Student> studentValidator = new StudentValidator();
        Repository<Integer, Student> studentRepository = new StudentFileRepository("src/main/java/Repository/Students.txt");

        Validator<Problem> problemValidator = new ProblemValidator();
        Repository<Integer, Problem> problemRepository = new ProblemFileRepository("src/main/java/Repository/Problems.txt");

        Validator<Grade> gradeValidator = new GradeValidator();
        Repository<Integer, Grade> gradeRepository = new GradeFileRepository("src/main/java/Repository/Grades.txt");

        ProblemsService problemService = new ProblemsService(problemRepository, problemValidator);
        StudentsService studentService = new StudentsService(studentRepository, gradeRepository, studentValidator, gradeValidator, problemService);
        Console console = new Console(studentService, problemService);

        console.menu();

    }

}
