package org.example.repository;

import org.example.domain.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentMysqlRepository implements StudentRepository {

    private static final String DB_URL = "jdbc:mysql://robot-do-user-1968994-0.b.db.ondigitalocean.com:25060/bielozertsev";
    private static final String DB_USER = "doadmin";
    private static final String DB_PASSWORD = "AVNS_I6wlDKjGszZn1wvLr9t";
    private static final String SELECT_FROM_STUDENT = "SELECT * FROM STUDENT";
    private static final String SELECT_STUDENT_BY_ID = "SELECT * FROM STUDENT WHERE ID = ?";

    private static final String INSERT_STUDENT = "INSERT INTO STUDENT  (SURNAME, AGE, GROUP_ID) VALUES (?, ?, ?)";


    @Override
    public void save(Student student) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(INSERT_STUDENT);
            ps.setString(1, student.getSurname());
            ps.setInt(2, student.getAge());
            ps.setString(3, student.getGroupID());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Student> findAll() {
        List<Student> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_FROM_STUDENT)) {
            while (rs.next()) {
                Student student = Student.builder()
                        .id(rs.getInt("ID"))
                        .surname(rs.getString("SURNAME"))
                        .age(rs.getInt("AGE"))
                        .groupID(rs.getString("GROUP_ID"))
                        .build();

                result.add(student);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Student findById(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            ps = conn.prepareStatement(SELECT_STUDENT_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            rs.next();
            return Student.builder()
                    .id(rs.getInt("ID"))
                    .surname(rs.getString("SURNAME"))
                    .age(rs.getInt("AGE"))
                    .groupID(rs.getString("GROUP_ID"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ps.close();
                rs.close();
            } catch (SQLException e) {

            }
        }
    }
}
