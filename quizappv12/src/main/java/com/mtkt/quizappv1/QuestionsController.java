/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mtkt.quizappv1;

import com.mtkt.pojo.Category;
import com.mtkt.pojo.Question;
import com.mtkt.services.CategoryServices;
import com.mtkt.services.question.QuestionServices; // ĐÃ THÊM: Import QuestionServices
import java.net.URL;
import java.sql.SQLException; // ĐÃ THÊM: Import SQLException
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level; // ĐÃ THÊM: Import Level
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author mai thoa
 */
public class QuestionsController implements Initializable {

    @FXML 
    private ComboBox<Category> cbCates;
    @FXML 
    private TableView<Question> tvQuestions;

    private String sql;
    
    // Sử dụng duy nhất một đối tượng dịch vụ này cho toàn bộ class
    private final CategoryServices categoryServices = new CategoryServices();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Tự động gọi hàm đổ dữ liệu vào ComboBox khi giao diện mở lên
        this.loadCategoriesToComboBox();
        
        // GIỮ NGUYÊN CẤU TRÚC tự khởi tạo Service trong hàm của bạn
        QuestionServices s2 = new QuestionServices();
        try {
            // ĐÃ SỬA: Thay 's' bằng 'categoryServices' để đúng biến hiện có
            this.cbCates.setItems(FXCollections.observableList(categoryServices.getCates()));
            
            // ĐÃ SỬA: 'setIteams' -> 'setItems' | 'getQuestion' -> 'getQuestions'
            this.tvQuestions.setItems(FXCollections.observableList(s2.getQuestions()));
            
        } catch (Exception ex) { // Đạt Exception tổng quát để bắt các lỗi từ Service
            // ĐÃ SỬA: Sửa lại vị trí dấu ngoặc đóng của Logger cho đúng cú pháp
            Logger.getLogger(QuestionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    /**
     * Hàm phụ trách việc lấy dữ liệu từ Service và nạp vào ComboBox
     */
    private void loadCategoriesToComboBox() {
        try {
            // 1. Gọi Service lấy danh sách danh mục từ Database
            List<Category> cates = this.categoryServices.getCates();
            
            // 2. Xóa dữ liệu cũ (nếu có) và nạp dữ liệu mới một cách an toàn vào ComboBox
            if (cates != null) {
                this.cbCates.setItems(FXCollections.observableArrayList(cates));
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi load danh mục lên ComboBox: " + e.getMessage());
        }
    }
    private void loadColumns(){
    
    TableColumn colId = new TableColumn("Id");
    colId.setCellValueFactory(new PropertyValueFactory("id"));
    colId.setPrefWidth(100);
    
     TableColumn colContent = new TableColumn("Nội dung câu hỏi");
    colId.setCellValueFactory(new PropertyValueFactory("content"));
    colId.setPrefWidth(300);
    
    this.tvQuestions.getColumns().addAll(colId, colContent);
    
    }
}