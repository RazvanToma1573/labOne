package Repository;

import Domain.Student;
import com.sun.tools.javac.util.Pair;


import javax.script.ScriptEngine;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class StudentDBRepository implements SortedRepository<Integer, Student> {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "parola";

    @Override
    public Iterable<Student> findAll(Sort sortObj) {
        List<Student> result = new ArrayList<>();
        String sql = "select * from students";
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                Student student = new Student(firstName, lastName);
                student.setId(id);
                result.add(student);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        List<Pair<String, Boolean>> criteria = sortObj.getCriteria();
        for (Pair<String, Boolean> c : criteria) {
            result = result.stream().sorted((s1, s2) -> {
                        try {
                            Field field1 = s1.getClass().getDeclaredField(c.fst);
                            field1.setAccessible(true);
                            Field field2 = s2.getClass().getDeclaredField(c.fst);
                            field2.setAccessible(true);
                            String str1 = field1.get(s1).toString();
                            String str2 = field2.get(s2).toString();
                            field1.setAccessible(false);
                            field2.setAccessible(false);
                            if (c.snd) {
                                return str2.compareTo(str1);
                            } else {
                                return  str1.compareTo(str2);
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
    public Optional<Student> findOne(Integer integer) {
        String sql = "select * from students where id = ?";
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = integer;
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                Student student = new Student(firstName, lastName);
                student.setId(id);
                return Optional.of(student);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Student> findAll() {
        List<Student> result = new ArrayList<>();
        String sql = "select * from students";
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                Student student = new Student(firstName, lastName);
                student.setId(id);
                result.add(student);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }


    @Override
    public Optional<Student> save(Student entity) {
        try {
            String sql = "insert into students(id, firstName, lastName) values(?, ?, ?)";
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getFirstName());
            preparedStatement.setString(3, entity.getLastName());
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e ) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Student> delete(Integer integer) {
        try{
            String sql = "delete from students where id = ?";
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, integer);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Student> update(Student entity) {
        String sql = "update students set firstName=?, lastName=? where id=? ";
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setInt(3, entity.getId());
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
}
