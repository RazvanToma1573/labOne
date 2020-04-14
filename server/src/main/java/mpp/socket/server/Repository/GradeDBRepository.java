package mpp.socket.server.Repository;



import mpp.socket.common.Domain.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Component
public class GradeDBRepository implements SortedRepository<Integer, Grade> {

    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    public Iterable<Grade> findAll(Sort sortObj) {
        List<Grade> result = new ArrayList<>();
        findAll().forEach(result::add);

        try{
            final Class gradeClass;

            gradeClass = Class.forName("mpp.socket.common.Domain.Grade");

            Optional<Comparator<Grade>> comparator = sortObj.getCriteria().stream()
                    .map(cr ->{
                        try{
                            final Field field = gradeClass.getDeclaredField(cr.fst);
                            field.setAccessible(true);
                            return (Comparator<Grade>) (grade, t1) -> {
                                try{
                                    Comparable c1 = (Comparable)field.get(grade);
                                    Comparable c2 = (Comparable)field.get(t1);
                                    return cr.snd ? c2.compareTo(c1) : c1.compareTo(c2);
                                } catch (IllegalAccessException e){
                                    System.out.println(e.getMessage());
                                }
                                return 0;
                            };
                        } catch (NoSuchFieldException e){
                            System.out.println(e.getMessage());
                        }
                        return null;
                    })
                    .reduce((c1, c2) -> c1.thenComparing(c2));
            Collections.sort(result, comparator.get());

        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public Optional<Grade> findOne(Integer id) {
        String sql = "select * from grades where id=" + id.toString();

        return Optional.of(jdbcOperations.query(sql, (rs, rowNum) -> {
            int gradeId = rs.getInt("id");
            int studentId = rs.getInt("studentid");
            int problemId = rs.getInt("problemid");
            int actualGrade = rs.getInt("actualgrade");

            Grade grade = new Grade(studentId, problemId, actualGrade);
            grade.setId(gradeId);
            return grade;
        }).get(0));


    }

    @Override
    public Iterable<Grade> findAll() {
        List<Grade> grades = new ArrayList<>();
        String sql = "select * from grades";
        return jdbcOperations.query(sql, (rs, rowNum) -> {
            int gradeId = rs.getInt("id");
            int studentId = rs.getInt("studentid");
            int problemId = rs.getInt("problemid");
            int actualGrade = rs.getInt("actualgrade");

            Grade grade = new Grade(studentId, problemId, actualGrade);
            grade.setId(gradeId);
            return grade;
        });
    }

    @Override
    public Optional<Grade> save(Grade entity) {
        String sql = "insert into grades(id, studentid, problemid, actualgrade) values(?,?,?,?)";

        jdbcOperations.update(sql, entity.getId(), entity.getStudent(), entity.getProblem(), entity.getActualGrade());
        return Optional.empty();
    }

    @Override
    public Optional<Grade> delete(Integer id) {
        String sql = "delete from grades where id=?";
        jdbcOperations.update(sql, id);
        return Optional.empty();
    }

    @Override
    public Optional<Grade> update(Grade entity) {
        String sql = "update grades set studentid=?, problemid=?, actualgrade=? where id=?";
        jdbcOperations.update(sql, entity.getStudent(), entity.getProblem(), entity.getActualGrade(), entity.getId());
        return Optional.empty();
    }
}
