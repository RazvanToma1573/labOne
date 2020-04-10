package mpp.socket.server.Repository;



import mpp.socket.server.Domain.Grade;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

@Component
public class GradeDBRepository implements SortedRepository<Integer, Grade> {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "parola";

    @Override
    public Iterable<Grade> findAll(Sort sortObj) {
        List<Grade> result = new ArrayList<>();
        findAll().forEach(result::add);

        try{
            final Class gradeClass;

            gradeClass = Class.forName("Domain.Grade");

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
        String sql = "select * from grades where id=?";
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            int gradeId = resultSet.getInt("id");
            int studentId = resultSet.getInt("studentid");
            int problemId = resultSet.getInt("problemid");
            int actualGrade = resultSet.getInt("actualgrade");
            Grade grade = new Grade(studentId, problemId, actualGrade);
            grade.setId(gradeId);
            return Optional.ofNullable(grade);
        } catch (SQLException exception){
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Grade> findAll() {
        List<Grade> grades = new ArrayList<>();
        String sql = "select * from grades";
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int studentId = resultSet.getInt("studentid");
                int problemId = resultSet.getInt("problemid");
                int actualGrade = resultSet.getInt("actualgrade");
                Grade grade = new Grade(studentId, problemId, actualGrade);
                grade.setId(id);
                grades.add(grade);
            }
            return grades;
        } catch (SQLException exception){
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Grade> save(Grade entity) {
        String sql = "insert into grades(id, studentid, problemid, actualgrade) values(?,?,?,?)";
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setInt(2, entity.getStudent());
            preparedStatement.setInt(3, entity.getProblem());
            preparedStatement.setInt(4, entity.getActualGrade());
            preparedStatement.executeUpdate();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Grade> delete(Integer id) {
        String sql = "delete from grades where id=?";
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Grade> update(Grade entity) {
        String sql = "update grades set studentid=?, problemid=?, actualgrade=? where id=?";
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, entity.getStudent());
            preparedStatement.setInt(2, entity.getProblem());
            preparedStatement.setInt(3, entity.getActualGrade());
            preparedStatement.setInt(4, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
        return Optional.empty();
    }
}
