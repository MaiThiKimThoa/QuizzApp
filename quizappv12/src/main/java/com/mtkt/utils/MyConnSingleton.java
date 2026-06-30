/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mtkt.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mai thoa
 */
public class MyConnSingleton {
    // ĐÃ SỬA: Thay MyAlertSingleton thành MyConnSingleton cho đúng tên Class
    private static MyConnSingleton instance; 
    private Connection conn;
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MyConnSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private MyConnSingleton() {
        try {
            // Nhớ kiểm tra xem port MySQL của bạn có phải mặc định không, nếu đổi port (VD: 3306) thì sửa thành "jdbc:mysql://localhost:3306/quizdb"
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost/quizdb", "root", "root");
        } catch (SQLException ex) {
            Logger.getLogger(MyConnSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static MyConnSingleton getInstance() {
        // Đã hoạt động chính xác sau khi sửa kiểu dữ liệu của biến 'instance' ở trên
        if (instance == null) {
            instance = new MyConnSingleton();
        }
        return instance;
    }
    
    public Connection connect() {
        return this.conn;
    }
    
    public void close() {
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(MyConnSingleton.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
