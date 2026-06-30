/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mtkt.services.question;

import com.mtkt.pojo.Category;
import com.mtkt.pojo.Question;
import com.mtkt.utils.MyConnSingleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mai thoa
 */
public class QuestionServices {

    public List<Question> getQuestions() {
        // Đã sửa tên biến đồng nhất thành 'questions' (số nhiều)
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM question"; 

        // Sử dụng try-with-resources để tự động đóng kết nối, tránh rò rỉ bộ nhớ
        // ĐÃ SỬA: Dùng prepareStatement thay vì prepareCall, và stm.executeQuery() không truyền tham số sql
        try (Connection conn = MyConnSingleton.getInstance().connect();
             PreparedStatement stm = conn.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                
                // GIỮ NGUYÊN cấu trúc lấy dữ liệu Category từ file gốc của bạn
                // Lưu ý: Hãy chắc chắn trong bảng 'question' của bạn có cột 'name' (hoặc bạn đã JOIN bảng)
                String name = rs.getString("name"); 

                // In ra màn hình kiểm tra
                System.out.printf("%d - %s\n", id, name);

                // Khởi tạo đối tượng Category và gán dữ liệu
                Category c = new Category();
                c.setId(id);         
                c.setName(name);
                
                // Lấy nội dung câu hỏi
                String content = rs.getString("content");
                
                // Thêm vào danh sách (Sử dụng đúng tên biến 'questions')
                // Nếu Builder của Question có nhận Category, bạn có thể truyền thêm .setCategory(c) vào đây nếu cần
                questions.add(new Question.Builder()
                                        .setId(id)
                                        .setContent(content)
                                        .build());
            }
            
        } catch (SQLException ex) {
            System.err.println("Lỗi kết nối CSDL hoặc truy vấn: " + ex.getMessage());
        }

        // ĐÃ SỬA: Trả về đúng tên biến 'questions' ở đầu hàm
        return questions; 
    }
}