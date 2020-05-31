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
public class ProblemsServiceTest {
    @Autowired
    private IProblemService problemService;

    @Test
    public void findAll() {
        List<Problem> problems = problemService.findAll();
        assertEquals("There should be 2 problems", 3, problems.size());
    }

    @Test
    public void addProblem() {
        Problem problem = Problem.builder()
                .description("problem")
                .difficulty("easy")
                .build();
        problem.setId(10000);
        int size = problemService.findAll().size();
        problemService.add(problem.getId(), problem.getDescription(), problem.getDifficulty());
        assertEquals("There should be " + size + "+1 problems", size+1, problemService.findAll().size());
    }

    @Test
    public void updateProblem() {
        int id = 2000;
        problemService.update(id, "Update", "easy");
        assertEquals("Description - Update", "Update", problemService.getById(id).getDescription());
        assertEquals("Difficulty - easy", "easy", problemService.getById(id).getDifficulty());
    }

    @Test
    public void deleteProblem() {
        int id = 2001;
        int size = problemService.findAll().size();
        problemService.remove(id);
        assertEquals("There should be " + size + "-1 problems", size-1, problemService.findAll().size());

    }
}
