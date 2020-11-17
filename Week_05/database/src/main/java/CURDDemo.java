import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CURDDemo {

    private static final String jdbcUrl = "jdbc:mysql://localhost/learnjdbc?useSSL=false&characterEncoding=utf8";
    private static final String jdbcUsername = "root";
    private static final String jdbcPassword = "qwe123";

    public static void main(String[] args) throws Exception {
        List<Student> students = queryStudents(3, 90);
        students.forEach(System.out::println);
        updateById(1, 92);
        insertStudent("zhangsan", 1, 2, 87);
        deleteByName("zhangsan");
    }

    private static List<Student> queryStudents(int grade, int score) throws SQLException {
        List<Student> students = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            try (PreparedStatement ps = conn
                    .prepareStatement("SELECT * FROM students WHERE grade = ? AND score >= ?")) {
                ps.setInt(1, grade); // 第一个参数grade=?
                ps.setInt(2, score); // 第二个参数score=?
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        students.add(extractRow(rs));
                    }
                }
            }
        }
        return students;
    }

    private static void updateById(int id, int score) throws SQLException {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            try (PreparedStatement ps = conn.prepareStatement("update students set score = ? where id = ?")) {
                ps.setInt(1, score);
                ps.setInt(2, id);
                ps.execute();
            }
        }
    }

    private static void insertStudent(String name, int gender, int grade, int score) throws SQLException {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            try (PreparedStatement ps = conn.prepareStatement("insert into students(name, gender, grade, score) values(?,?,?,?)")) {
                ps.setString(1, name);
                ps.setInt(2, gender);
                ps.setInt(3, grade);
                ps.setInt(4, score);
                ps.execute();
            }
        }
    }

    private static void deleteByName(String name) throws SQLException {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            try (PreparedStatement ps = conn.prepareStatement("delete from students where name = ?")) {
                ps.setString(1, name);
                ps.execute();
            }
        }
    }

    private static Student extractRow(ResultSet rs) throws SQLException {
        Student std = new Student();
        std.setId(rs.getLong("id"));
        std.setName(rs.getString("name"));
        std.setGender(rs.getBoolean("gender"));
        std.setGrade(rs.getInt("grade"));
        std.setScore(rs.getInt("score"));
        return std;
    }


}
