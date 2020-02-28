package Domain.Validators;

import Domain.Student;

import java.util.Arrays;

public class StudentValidator implements Validator<Student> {
    @Override
    public void validate(Student student) throws ValidatorException {
        if(student.getId()<1) throw new ValidatorException("Invalid student ID");
        if(!student.getFirstName().matches("[a-zA-Z]+")) throw new ValidatorException("Invalid first name");
        if(!student.getLastName().matches("[a-zA-Z]+")) throw new ValidatorException("Invalid last name");
    }
}
