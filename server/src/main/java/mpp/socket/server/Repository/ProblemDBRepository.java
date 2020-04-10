package mpp.socket.server.Repository;



import mpp.socket.server.Domain.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class ProblemDBRepository implements SortedRepository<Integer, Problem>{

    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    public Iterable<Problem> findAll(Sort sortObj) {
        List<Problem> result = new ArrayList<>();
        findAll().forEach(result::add);
        try{
            final Class problemClass;
            final Class baseClass;

            problemClass = Class.forName("Domain.Problem");
            baseClass = Class.forName("Domain.BaseEntity");

            Optional<Comparator<Problem>> comparator = sortObj.getCriteria().stream()
                    .map(cr ->{
                        try{
                            final Field field = cr.fst.equals("id") ?
                                    baseClass.getDeclaredField(cr.fst) :
                                    problemClass.getDeclaredField(cr.fst);
                            field.setAccessible(true);
                            return (Comparator<Problem>) (problem, t1) -> {
                                try{
                                    Comparable c1 = (Comparable)field.get(problem);
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
    public Optional<Problem> findOne(Integer integer) {
        String sql = "select * from problems where id=" + integer.toString();
        try{
            Class.forName("org.postgresql.Driver");
            return Optional.of(jdbcOperations.query(sql, (rs, rowNum) -> {
                String description = rs.getString("description");
                String difficulty = rs.getString("difficulty");
                Problem problem = new Problem(description, difficulty);
                problem.setId(integer);
                return problem;
            }).get(0));

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Problem> findAll() {
        List<Problem> result = new ArrayList<>();
        String sql = "select * from problems";
        try{
            Class.forName("org.postgresql.Driver");
            return jdbcOperations.query(sql, (rs, rowNum) -> {
                int id = rs.getInt("id");
                String description = rs.getString("description");
                String difficulty = rs.getString("difficulty");
                Problem problem = new Problem(description, difficulty);
                problem.setId(id);

                return problem;
            });
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public Optional<Problem> save(Problem entity) {
        String sql = "insert into problems(id, description, difficulty) values(?, ?, ?)";
        try{
            Class.forName("org.postgresql.Driver");
            jdbcOperations.update(sql, entity.getId(), entity.getDescription(), entity.getDifficulty());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Problem> delete(Integer integer) {
        String sql = "delete from problems where id=?";
        try{
            Class.forName("org.postgresql.Driver");
            jdbcOperations.update(sql, integer);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Problem> update(Problem entity) {
        String sql = "update problems set description=?, difficulty=? where id=?";
        try{
            Class.forName("org.postgresql.Driver");
            jdbcOperations.update(sql, entity.getDescription(), entity.getDifficulty(), entity.getId());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
}
