package ro.mpp.core.Repository;

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
import org.springframework.transaction.annotation.Transactional;
import ro.mpp.core.Domain.Student;
import ro.mpp.core.ITConfig;

import java.util.List;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-data.xml")
public class StudentRepoTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void findAll() {
        List<Student> students = studentRepository.findAll();
        assertEquals("There should be 3 students", 3, students.size());
    }

    @Test
    public void save() {
        Student student = Student.builder()
                .firstName("Student")
                .lastName("Student")
                .build();
        student.setId(999);
        studentRepository.save(student);
        assertEquals("There should be 4 students", 4, studentRepository.findAll().size());
    }

    @Test
    public void delete() {
        studentRepository.delete(1000);
        assertEquals("There should be 2 students", 2, studentRepository.findAll().size());
    }

    @Test
    @Transactional
    public void update() {
        studentRepository.findOne(1000)
                .setFirstName("Update");
        assertEquals("Should be update", "Update", studentRepository.findOne(1000).getFirstName());
    }
}
