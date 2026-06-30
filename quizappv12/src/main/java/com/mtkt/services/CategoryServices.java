package com.mtkt.services;

import com.mtkt.pojo.Category;
import com.mtkt.utils.MyConnSingleton;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mai thoa
 */
public class CategoryServices {

    public List<Category> getCates() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM category"; // Đã sửa 'SELET' thành 'SELECT'

        // Sử dụng try-with-resources để tự động đóng kết nối, tránh rò rỉ bộ nhớ
        try (Connection conn = MyConnSingleton.getInstance().connect();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                // In ra màn hình kiểm tra (Sửa định dạng %s cho String và đưa biến ra ngoài chuỗi)
                System.out.printf("%d - %s\n", id, name);

                // Khởi tạo đối tượng Category và thêm vào danh sách
                Category c = new Category();
                c.setId(id);         // Đảm bảo class Category của bạn có các hàm setter này
                c.setName(name);
                
                categories.add(c);
            }
            
        } catch (SQLException ex) {
            System.err.println("Lỗi kết nối CSDL hoặc truy vấn: " + ex.getMessage());
        }

        return categories; // Trả về danh sách kết quả
    }
}
