package mpp.socket.common.Repository;




import mpp.socket.common.Domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Component
public class StudentDBRepository implements SortedRepository<Integer, Student> {
    /*
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "parola";
     */
    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    public Iterable<Student> findAll(Sort sortObj) {
        List<Student> result = new ArrayList<>();
        findAll().forEach(result::add);
        try{
            final Class studentClass;
            final Class baseClass;

            studentClass = Class.forName("mpp.socket.common.Domain.Student");
            baseClass = Class.forName("mpp.socket.common.Domain.BaseEntity");

            Optional<Comparator<Student>> comparator = sortObj.getCriteria().stream()
                    .map(cr ->{
                        try{
                            final Field field = cr.fst.equals("id") ?
                                    baseClass.getDeclaredField(cr.fst) :
                                    studentClass.getDeclaredField(cr.fst);
                            field.setAccessible(true);
                            return (Comparator<Student>) (student, t1) -> {
                                try{
                                    Comparable c1 = (Comparable)field.get(student);
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
    public Optional<Student> findOne(Integer integer) {
        /*
        String sql = "select * from students where id = ?";
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {postgres
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
         */
    String sql  = "select * from students where id = " + integer;
    return Optional.of(jdbcOperations.query(sql, (rs, rowNr) -> {
        int id = rs.getInt("id");
        String fName = rs.getString("firstName");
        String lName = rs.getString("lastName");
        Student student = new Student(fName, lName);
        student.setId(id);
        return student;
    }).get(0));
    }

    @Override
    public Iterable<Student> findAll() {
        /*
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

         */
        String sql  = "select * from students";
        return jdbcOperations.query(sql, (rs, rowNr) -> {
            int id = rs.getInt("id");
            String fName = rs.getString("firstName");
            String lName = rs.getString("lastName");
            Student student = new Student(fName, lName);
            student.setId(id);
            return student;
        });
    }


    @Override
    public Optional<Student> save(Student entity) {
        /*
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

         */
        String sql = "insert into students(id, firstName, lastName) values(?,?,?)";
        jdbcOperations.update(sql, entity.getId(), entity.getFirstName(), entity.getLastName());
        return Optional.empty();
    }

    @Override
    public Optional<Student> delete(Integer integer) {
        /*
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

         */
        String sql = "delete from students where id=?";
        jdbcOperations.update(sql, integer);
        return Optional.empty();
    }

    @Override
    public Optional<Student> update(Student entity) {
        /*
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

         */
        String sql = "update students set firstName=?, lastName=? where id=?";
        jdbcOperations.update(sql, entity.getFirstName(), entity.getLastName(), entity.getId());
        return Optional.empty();
    }
}
