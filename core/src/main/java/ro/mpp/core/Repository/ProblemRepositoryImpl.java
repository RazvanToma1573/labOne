package ro.mpp.core.Repository;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.ejb.HibernateEntityManager;
import org.springframework.transaction.annotation.Transactional;
import ro.mpp.core.Domain.Grade;
import ro.mpp.core.Domain.Problem;
import ro.mpp.core.Domain.Student;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

public class ProblemRepositoryImpl extends CustomRepositorySupport implements ProblemRepositoryCustom {
    @Override
    public List<Problem> findAllWithGradesAndStudentJPQL() {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select distinct p from Problem p " +
                "left join fetch p.grades g " +
                "left join fetch g.student"
        );
        return query.getResultList();
    }

    @Override
    public List<Problem> findAllWithGradesAndStudentCriteriaAPI() {
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Problem> query = criteriaBuilder.createQuery(Problem.class);
        query.distinct(Boolean.TRUE);
        Root<Problem> root = query.from(Problem.class);
        Fetch<Problem, Grade> problemGradeFetch = root.fetch("grades", JoinType.LEFT);
        problemGradeFetch.fetch("student", JoinType.LEFT);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    @Transactional
    public List<Problem> findAllWithGradesAndStudentSQL() {
        Session session = getEntityManager().unwrap(Session.class);

        org.hibernate.Query query = session.createSQLQuery("select distinct {p.*},{g.*},{s.*} " +
                "from problem p " +
                "left join grade g on p.id=g.problem_id " +
                "left join student s on g.student_id=s.id ")
                .addEntity("p", Problem.class)
                .addJoin("g", "p.grades")
                .addJoin("s", "g.student")
                .addEntity("p", Problem.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        return query.getResultList();
    }

    @Override
    public Problem findWithGradesAndStudentJPQL(int id) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select distinct p from Problem p " +
                "left join fetch p.grades g " +
                "left join fetch g.student " +
                "where p.id=:id"
        );
        query.setParameter("id", id);
        return (Problem)query.getResultList().get(0);
    }

    @Override
    public Problem findWithGradesAndStudentCriteriaAPI(int id) {
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Problem> query = criteriaBuilder.createQuery(Problem.class);
        query.distinct(Boolean.TRUE);
        Root<Problem> root = query.from(Problem.class);
        query.where(criteriaBuilder.equal(root.get("id"), id));
        Fetch<Problem, Grade> problemGradeFetch = root.fetch("grades", JoinType.LEFT);
        problemGradeFetch.fetch("student", JoinType.LEFT);

        return entityManager.createQuery(query).getResultList().get(0);
    }

    @Override
    @Transactional
    public Problem findWithGradesAndStudentSQL(int id) {
        Session session = getEntityManager().unwrap(Session.class);

        org.hibernate.Query query = session.createSQLQuery("select distinct {p.*},{g.*},{s.*} " +
                "from problem p " +
                "left join grade g on p.id=g.problem_id " +
                "left join student s on g.student_id=s.id " +
                "where p.id=:id")
                .addEntity("p", Problem.class)
                .setParameter("id", id)
                .addJoin("g", "p.grades")
                .addJoin("s", "g.student")
                .addEntity("p", Problem.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        return (Problem)query.getResultList().get(0);
    }

    @Override
    public List<Problem> findAllByDescriptionJPQL(String description) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select distinct p from Problem p " +
                "left join fetch p.grades g " +
                "left join fetch g.student " +
                "where p.description=:description"
        );
        query.setParameter("description", description);
        return query.getResultList();
    }

    @Override
    public List<Problem> findAllByDescriptionCriteriaAPI(String description) {
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Problem> query = criteriaBuilder.createQuery(Problem.class);
        query.distinct(Boolean.TRUE);
        Root<Problem> root = query.from(Problem.class);
        query.where(criteriaBuilder.equal(root.get("description"), description));
        Fetch<Problem, Grade> problemGradeFetch = root.fetch("grades", JoinType.LEFT);
        problemGradeFetch.fetch("student", JoinType.LEFT);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    @Transactional
    public List<Problem> findAllByDescriptionSQL(String description) {
        Session session = getEntityManager().unwrap(Session.class);

        org.hibernate.Query query = session.createSQLQuery("select distinct {p.*},{g.*},{s.*} " +
                "from problem p " +
                "left join grade g on p.id=g.problem_id " +
                "left join student s on g.student_id=s.id " +
                "where p.description=:description")
                .addEntity("p", Problem.class)
                .setParameter("description", description)
                .addJoin("g", "p.grades")
                .addJoin("s", "g.student")
                .addEntity("p", Problem.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        return query.getResultList();
    }
}
