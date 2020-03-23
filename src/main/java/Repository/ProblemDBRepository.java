package Repository;

import Domain.Problem;
import Domain.Student;
import com.sun.tools.javac.util.Pair;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class ProblemDBRepository implements SortedRepository<Integer, Problem>{

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "parola";

    @Override
    public Iterable<Problem> findAll(Sort sortObj) {
        List<Problem> result = new ArrayList<>();
        String sql = "select * from problems";
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("description");
                String difficulty = resultSet.getString("difficulty");
                Problem problem = new Problem(description, difficulty);
                problem.setId(id);
                result.add(problem);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

        sortObj.getCriteria().stream().forEach(cr ->{
            try {
                Class problemClass = null;
                Class baseClass = null;
                final Field field;
                problemClass = Class.forName("Domain.Problem");
                baseClass = Class.forName("Domain.BaseEntity");
                field = cr.fst.equals("id") ? baseClass.getDeclaredField(cr.fst) : problemClass.getDeclaredField(cr.fst);
                field.setAccessible(true);
                Collections.sort(result, new Comparator<Problem>() {
                    @Override
                    public int compare(Problem problem1, Problem problem2) {
                        try{
                            Comparable c1 = (Comparable)field.get(problem1);
                            Comparable c2 = (Comparable)field.get(problem2);
                            return cr.snd ? c2.compareTo(c1) : c1.compareTo(c2);
                        } catch (IllegalAccessException e){
                            System.out.println(e.getMessage());
                        }
                        return 0;
                    }
                });
                field.setAccessible(false);
            } catch (ClassNotFoundException | NoSuchFieldException e) {
                System.out.println(e.getMessage());
            }
        });
        return result;
    }

    @Override
    public Optional<Problem> findOne(Integer integer) {
        String sql = "select * from problems where id=?";
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = integer;
                String description = resultSet.getString("description");
                String difficulty = resultSet.getString("difficulty");
                Problem problem = new Problem(description, difficulty);
                problem.setId(id);
                return Optional.of(problem);
            }
        } catch (ClassNotFoundException | SQLException e) {
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
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("description");
                String difficulty = resultSet.getString("difficulty");
                Problem problem = new Problem(description, difficulty);
                problem.setId(id);
                result.add(problem);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public Optional<Problem> save(Problem entity) {
        String sql = "insert into problems(id, description, difficulty) values(?, ?, ?)";
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setString(3, entity.getDifficulty());
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Problem> delete(Integer integer) {
        String sql = "delete from problems where id=?";
        try{
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
    public Optional<Problem> update(Problem entity) {
        String sql = "update problems set description=?, difficulty=? where id=?";
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getDescription());
            preparedStatement.setString(2, entity.getDifficulty());
            preparedStatement.setInt(3, entity.getId());
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
}
