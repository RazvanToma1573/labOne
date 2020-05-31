package ro.mpp.core.Repository;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.ejb.HibernateEntityManager;
import org.springframework.transaction.annotation.Transactional;
import ro.mpp.core.Domain.Grade;
import ro.mpp.core.Domain.Student;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
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
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> query = criteriaBuilder.createQuery(Student.class);
        query.distinct(Boolean.TRUE);
        Root<Student> root = query.from(Student.class);
        Fetch<Student, Grade> studentGradeFetch = root.fetch("grades", JoinType.LEFT);
        studentGradeFetch.fetch("problem", JoinType.LEFT);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    @Transactional
    public List<Student> findAllWithGradesAndProblemSQL() {
        Session session = getEntityManager().unwrap(Session.class);

        org.hibernate.Query query = session.createSQLQuery("select distinct {s.*},{g.*},{p.*} " +
                "from student s " +
                "left join grade g on s.id=g.student_id " +
                "left join problem p on g.problem_id=p.id")
                .addEntity("s", Student.class)
                .addJoin("g", "s.grades")
                .addJoin("p", "g.problem")
                .addEntity("s", Student.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        return query.getResultList();
    }

    @Override
    public Student findWithGradesAndProblemJPQL(int id) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select distinct s from Student s " +
                "left join fetch s.grades g " +
                "left join fetch g.problem " +
                "where s.id=:id"
        );
        query.setParameter("id", id);
        return (Student)query.getResultList().get(0);
    }

    @Override
    public Student findWithGradesAndProblemCriteriaAPI(int id) {
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> query = criteriaBuilder.createQuery(Student.class);
        query.distinct(Boolean.TRUE);
        Root<Student> root = query.from(Student.class);
        query.where(criteriaBuilder.equal(root.get("id"), id));
        Fetch<Student, Grade> studentGradeFetch = root.fetch("grades", JoinType.LEFT);
        studentGradeFetch.fetch("problem", JoinType.LEFT);

        return entityManager.createQuery(query).getResultList().get(0);
    }

    @Override
    @Transactional
    public Student findWithGradesAndProblemSQL(int id) {
        Session session = getEntityManager().unwrap(Session.class);

        org.hibernate.Query query = session.createSQLQuery("select distinct {s.*},{g.*},{p.*} " +
                "from student s " +
                "left join grade g on s.id=g.student_id " +
                "left join problem p on g.problem_id=p.id " +
                "where s.id=:id")
                .addEntity("s", Student.class)
                .setParameter("id", id)
                .addJoin("g", "s.grades")
                .addJoin("p", "g.problem")
                .addEntity("s", Student.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        return (Student)query.getResultList().get(0);
    }

    @Override
    public List<Student> findAllByFirstNameJPQL(String firstName) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select distinct s from Student s " +
                "left join fetch s.grades g " +
                "left join fetch g.problem " +
                "where s.firstName=:firstName"
        );
        query.setParameter("firstName", firstName);
        return query.getResultList();
    }

    @Override
    public List<Student> findAllByFirstNameCriteriaAPI(String firstName) {
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> query = criteriaBuilder.createQuery(Student.class);
        query.distinct(Boolean.TRUE);
        Root<Student> root = query.from(Student.class);
        query.where(criteriaBuilder.equal(root.get("firstName"), firstName));
        Fetch<Student, Grade> studentGradeFetch = root.fetch("grades", JoinType.LEFT);
        studentGradeFetch.fetch("problem", JoinType.LEFT);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    @Transactional
    public List<Student> findAllByFirstNameSQL(String firstName) {
        Session session = getEntityManager().unwrap(Session.class);

        org.hibernate.Query query = session.createSQLQuery("select distinct {s.*},{g.*},{p.*} " +
                "from student s " +
                "left join grade g on s.id=g.student_id " +
                "left join problem p on g.problem_id=p.id " +
                "where s.firstName=:firstName")
                .addEntity("s", Student.class)
                .setParameter("firstName", firstName)
                .addJoin("g", "s.grades")
                .addJoin("p", "g.problem")
                .addEntity("s", Student.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        return query.getResultList();
    }
}
