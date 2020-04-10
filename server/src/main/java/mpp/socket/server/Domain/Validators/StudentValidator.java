package mpp.socket.server.Domain.Validators;



import mpp.socket.server.Domain.Student;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StudentValidator implements Validator<Student> {

    /**
     * Checks:
     * -id greater than 0
     * -first name has 1 or more letters
     * -last name has 1 or more letters
     * @param student Student to be checked.
     * @throws ValidatorException Custom exception.
     */
    @Override
    public void validate(Student student) throws ValidatorException {
        if(student.getId()<1) throw new ValidatorException("Invalid student ID");
        if(!student.getFirstName().matches("[a-zA-Z]+")) throw new ValidatorException("Invalid first name");
        if(!student.getLastName().matches("[a-zA-Z]+")) throw new ValidatorException("Invalid last name");
    }
}
