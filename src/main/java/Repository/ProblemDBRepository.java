package Repository;

import Domain.Problem;
import com.sun.tools.javac.util.Pair;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
                            return str1.compareTo(str2);
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
