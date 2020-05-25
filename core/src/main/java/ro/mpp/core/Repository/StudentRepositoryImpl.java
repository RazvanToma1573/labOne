package ro.mpp.core.Repository;

import ro.mpp.core.Domain.Student;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class StudentRepositoryImpl extends CustomRepositorySupport implements StudentRepositoryCustom {

    @Override
    public List<Student> findAllWithGradesAndProblemJPQL() {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select distinct s from Student s " +
                "left join fetch s.grades g " +
                "left join fetch g.problem"
        );
        return query.getResultList();
    }

    @Override
    public List<Student> findAllWithGradesAndProblemCriteriaAPI() {
        return null;
    }
}
