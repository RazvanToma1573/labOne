package ro.mpp.core.Service;


import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import ro.mpp.core.Domain.Student;
import ro.mpp.core.ITConfig;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-data.xml")
public class StudentsServiceTest {
    @Autowired
    private IStudentService studentService;

    @Test
    public void findAll() throws Exception {
        List<Student> students = studentService.findAll();
        assertEquals("There should be 3 students", 3, students.size());
    }

    @Test
    public void addStudent() {
        Student student = Student.builder()
                .firstName("Student")
                .lastName("Student")
                .build();
        student.setId(999);
        int size = studentService.findAll().size();
        studentService.add(student.getId(), student.getFirstName(), student.getLastName());
        assertEquals("There should be " + size + "+1 students", size+1, studentService.findAll().size());
    }

    @Test
    public void updateStudent() {
        int id = 1000;
        studentService.update(id, "Update", "Update");
        assertEquals("First Name - Update", "Update", studentService.getById(id).getFirstName());
        assertEquals("Last Name - Update", "Update", studentService.getById(id).getLastName());
    }

    @Test
    public void deleteStudent() {
        int id = 1001;
        int size = studentService.findAll().size();
        studentService.remove(id);
        assertEquals("There should be " + size + "-1 students", size-1, studentService.findAll().size());

    }
}
