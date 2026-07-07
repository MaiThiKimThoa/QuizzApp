package com.mtkt.services.question;

import com.mtkt.pojo.Category;
import com.mtkt.pojo.Choice;
import com.mtkt.pojo.Level; // Bổ sung import Level
import com.mtkt.pojo.Question;
import com.mtkt.utils.MyConnSingleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mai thoa
 */
public class QuestionServices {

    // ĐÃ SỬA: Bỏ throws thừa, sửa logic SQL và PreparedStatement
    public List<Question> getQuestions(String kw, Category cate, Level lvl) throws SQLException {
        List<Question> questions = new ArrayList<>();
        
        String sql = "SELECT * FROM question WHERE 1=1"; 
        List<Object> params = new ArrayList<>();

        if (kw != null && !kw.isEmpty()) {
            sql += " AND content LIKE CONCAT('%', ?, '%')"; // ĐÃ SỬA: Thêm khoảng trắng và AND
            params.add(kw);
        }

        if (cate != null) {
            sql += " AND category_id = ?"; // ĐÃ SỬA: Thêm khoảng trắng và AND
            params.add(cate.getId());
        }

        if (lvl != null) {
            sql += " AND level_id = ?"; // ĐÃ SỬA: Thêm khoảng trắng và AND
            params.add(lvl.getId());
        }

        // ĐÃ SỬA: Dùng try-with-resources để tự động đóng Connection/PreparedStatement/ResultSet
        try (Connection conn = MyConnSingleton.getInstance().connect();
             PreparedStatement stm = conn.prepareStatement(sql)) { // ĐÃ SỬA: Dùng prepareStatement thay vì prepareCall

            for (int i = 0; i < params.size(); i++) {
                stm.setObject(i + 1, params.get(i)); // ĐÃ SỬA: params.get(i) thay vì params.getClass(i)
            }

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String content = rs.getString("content");

                    int categoryId = rs.getInt("category_id");
                    Category c = new Category();
                    c.setId(categoryId);

                    Question question = new Question(id, content, c, null);
                    questions.add(question);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi kết nối CSDL hoặc truy vấn: " + ex.getMessage());
            throw ex; // Nên throw để Controller biết mà xử lý
        }

        return questions;
    }

    public void addQuestion(Question q, List<Choice> choices) throws SQLException {
        String insertQuestionSql = "INSERT INTO question (content, category_id, level_id) VALUES (?, ?, ?)";
        String insertChoiceSql = "INSERT INTO choice (content, is_correct, question_id) VALUES (?, ?, ?)";

        Connection conn = null;
        try {
            conn = MyConnSingleton.getInstance().connect();
            conn.setAutoCommit(false); 

            try (PreparedStatement stmQ = conn.prepareStatement(insertQuestionSql, Statement.RETURN_GENERATED_KEYS)) {
                stmQ.setString(1, q.getContent());
                stmQ.setInt(2, q.getCate() != null ? q.getCate().getId() : 0);
                stmQ.setInt(3, q.getLevel() != null ? q.getLevel().getId() : 0);
                
                stmQ.executeUpdate();

                int questionId = 0;
                try (ResultSet rs = stmQ.getGeneratedKeys()) {
                    if (rs.next()) {
                        questionId = rs.getInt(1);
                    }
                }

                if (questionId > 0 && choices != null && !choices.isEmpty()) {
                    try (PreparedStatement stmC = conn.prepareStatement(insertChoiceSql)) {
                        for (Choice choice : choices) {
                            stmC.setString(1, choice.getContent());
                            stmC.setBoolean(2, choice.isIsCorrect());
                            stmC.setInt(3, questionId);
                            stmC.addBatch();
                        }
                        stmC.executeBatch();
                    }
                } else {
                    throw new SQLException("Không lấy được ID câu hỏi mới hoặc danh sách đáp án trống.");
                }
            }

            conn.commit(); 

        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    conn.rollback(); 
                } catch (SQLException rollbackEx) {
                    System.err.println("Lỗi khi rollback dữ liệu: " + rollbackEx.getMessage());
                }
            }
            throw ex;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
                }
            }
        }
    }
}