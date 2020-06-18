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
import ro.mpp.core.Domain.Problem;
import ro.mpp.core.Domain.Student;
import ro.mpp.core.ITConfig;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-data-problems.xml")
public class ProblemRepoTest {
    @Autowired
    private ProblemRepository problemRepository;

    @Test
    public void findAll() {
        List<Problem> students = problemRepository.findAll();
        assertEquals("There should be 3 students", 3, students.size());
    }

    @Test
    public void save() {
        Problem problem = Problem.builder()
                .description("problem")
                .difficulty("easy")
                .build();
        problem.setId(1999);
        problemRepository.save(problem);
        assertEquals("There should be 4 students", 4, problemRepository.findAll().size());
    }

    @Test
    public void delete() {
        problemRepository.delete(2000);
        assertEquals("There should be 2 students", 2, problemRepository.findAll().size());
    }

    @Test
    @Transactional
    public void update() {
        problemRepository.findOne(2000)
                .setDescription("Update");
        assertEquals("Should be update", "Update", problemRepository.findOne(2000).getDescription());
    }
}
