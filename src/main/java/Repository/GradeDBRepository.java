package Repository;

import Domain.Grade;
import Domain.Validators.RepositoryException;
import com.sun.tools.javac.util.Pair;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class GradeDBRepository implements SortedRepository<Integer, Grade> {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "parola";

    @Override
    public Iterable<Grade> findAll(Sort sortObj) {
        List<Grade> result = new ArrayList<>();
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
                result.add(grade);
            }
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        List<Pair<String, Boolean>> criteria = sortObj.getCriteria();
        for (Pair<String, Boolean> c : criteria) {
            result = result.stream().sorted((s1, s2) -> {
                try {
                    Field field1 = s1.getClass().getDeclaredField(c.fst);
                    field1.setAccessible(true);
                    Field field2 = s2.getClass().getDeclaredField(c.fst);
                    field2.setAccessible(true);
                    if (field1.get(s1) instanceof Comparable && field2.get(s2) instanceof Comparable) {
                        Comparable str1 = (Comparable) field1.get(s1);
                        Comparable str2 = (Comparable) field2.get(s2);
                        field1.setAccessible(false);
                        field2.setAccessible(false);
                        if (c.snd) {
                            return str2.compareTo(str1);
                        } else {
                            return  str1.compareTo(str2);
                        }
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
                return -1;
            }).collect(Collectors.toList());
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
