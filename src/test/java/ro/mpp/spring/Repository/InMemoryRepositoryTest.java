package ro.mpp.spring.Repository;

import org.junit.Test;

public class InMemoryRepositoryTest {

    @Test
    public void findOne() {
        /*ro.mpp.spring.Repository<Integer, Student> studentRepository = new InMemoryRepository<Integer, Student>();
        ro.mpp.spring.Repository<Integer, Grade> gradeRepository = new InMemoryRepository<Integer, Grade>();
        ro.mpp.spring.Repository<Integer, Problem> problemRepository = new InMemoryRepository<Integer, Problem>();
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Grade> gradeValidator = new GradeValidator();
        Validator<Problem> problemValidator = new ProblemValidator();
        ProblemsService problemsService = new ProblemsService(problemRepository, problemValidator);
        StudentsService studentsService = new StudentsService(studentRepository, gradeRepository, studentValidator, gradeValidator, problemsService);

        Student student = new Student("Razvan","Toma");
        student.setId(1);

        try{
            studentsService.add(student);
        } catch(ValidatorException exception) {
            System.out.println("ValidatorException:" + exception.getMessage());
        } catch(IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }

        assertTrue(1 == studentRepository.findOne(1).get().getId());
    }

    @Test
    public void findAll() {
        ro.mpp.spring.Repository<Integer, Student> studentRepository = new InMemoryRepository<Integer, Student>();
        ro.mpp.spring.Repository<Integer, Grade> gradeRepository = new InMemoryRepository<Integer, Grade>();
        ro.mpp.spring.Repository<Integer, Problem> problemRepository = new InMemoryRepository<Integer, Problem>();
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Grade> gradeValidator = new GradeValidator();
        Validator<Problem> problemValidator = new ProblemValidator();
        ProblemsService problemsService = new ProblemsService(problemRepository, problemValidator);
        StudentsService studentsService = new StudentsService(studentRepository, gradeRepository, studentValidator, gradeValidator, problemsService);

        Student student1 = new Student("aaaa","bbbb");
        student1.setId(1);

        Student student2 = new Student("ccccc","dddd");
        student2.setId(2);

        try{
            studentsService.add(student1);
            studentsService.add(student2);
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
        ro.mpp.spring.Repository<Integer, Student> studentRepository = new InMemoryRepository<Integer, Student>();
        ro.mpp.spring.Repository<Integer, Grade> gradeRepository = new InMemoryRepository<Integer, Grade>();
        ro.mpp.spring.Repository<Integer, Problem> problemRepository = new InMemoryRepository<Integer, Problem>();
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Grade> gradeValidator = new GradeValidator();
        Validator<Problem> problemValidator = new ProblemValidator();
        ProblemsService problemsService = new ProblemsService(problemRepository, problemValidator);
        StudentsService studentsService = new StudentsService(studentRepository, gradeRepository, studentValidator, gradeValidator, problemsService);

        Student student1 = new Student("aaaa","bbbb");
        student1.setId(1);


        try{
            studentsService.add(student1);
        } catch(ValidatorException exception) {
            System.out.println("ValidatorException:" + exception.getMessage());
        } catch(IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }

        assertTrue("aaaa".equals(studentRepository.findOne(1).get().getFirstName()));


        Student student2 = new Student("bbbb","cccc");
        student2.setId(1);


        try{
            studentsService.add(student2);
        } catch(ValidatorException exception) {
            System.out.println("ValidatorException:" + exception.getMessage());
        } catch(IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }

        assertTrue("aaaa".equals(studentRepository.findOne(1).get().getFirstName()));

    }

    @Test
    public void delete() {
        ro.mpp.spring.Repository<Integer, Student> studentRepository = new InMemoryRepository<Integer, Student>();
        ro.mpp.spring.Repository<Integer, Grade> gradeRepository = new InMemoryRepository<Integer, Grade>();
        ro.mpp.spring.Repository<Integer, Problem> problemRepository = new InMemoryRepository<Integer, Problem>();
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Grade> gradeValidator = new GradeValidator();
        Validator<Problem> problemValidator = new ProblemValidator();
        ProblemsService problemsService = new ProblemsService(problemRepository, problemValidator);
        StudentsService studentsService = new StudentsService(studentRepository, gradeRepository, studentValidator, gradeValidator, problemsService);


        Student student1 = new Student("aaaa","bbbb");
        student1.setId(1);


        try{
            studentsService.add(student1);
        } catch(ValidatorException exception) {
            System.out.println("ValidatorException:" + exception.getMessage());
        } catch(IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }


        try{
            studentsService.remove(1);
        } catch(IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }

        Optional<Student> student = studentRepository.findOne(1);
        assertTrue(!student.isPresent());
    }

    @Test
    public void update() {
        ro.mpp.spring.Repository<Integer, Student> studentRepository = new InMemoryRepository<Integer, Student>();
        ro.mpp.spring.Repository<Integer, Grade> gradeRepository = new InMemoryRepository<Integer, Grade>();
        ro.mpp.spring.Repository<Integer, Problem> problemRepository = new InMemoryRepository<Integer, Problem>();
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Grade> gradeValidator = new GradeValidator();
        Validator<Problem> problemValidator = new ProblemValidator();
        ProblemsService problemsService = new ProblemsService(problemRepository, problemValidator);
        StudentsService studentsService = new StudentsService(studentRepository, gradeRepository, studentValidator, gradeValidator, problemsService);

        Student student1 = new Student("aaaa","bbbb");
        student1.setId(1);


        try{
            studentsService.add(student1);
        } catch(ValidatorException exception) {
            System.out.println("ValidatorException:" + exception.getMessage());
        } catch(IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }


        try{
            studentsService.update(1, "FIRST", "xxxx");
        } catch(ValidatorException exception) {
            System.out.println("ValidatorException:" + exception.getMessage());
        } catch(IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }

        try{
            studentsService.update(1, "LAST", "yyyy");
        } catch(ValidatorException exception) {
            System.out.println("ValidatorException:" + exception.getMessage());
        } catch(IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }

        assertTrue("xxxx".equals(studentRepository.findOne(1).get().getFirstName()));
        assertTrue("yyyy".equals(studentRepository.findOne(1).get().getLastName()));*/
    }
}