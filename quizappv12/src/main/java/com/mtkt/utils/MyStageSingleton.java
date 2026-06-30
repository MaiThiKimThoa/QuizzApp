/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mtkt.utils;

import com.mtkt.quizappv1.App;
import com.mtkt.utils.themes.ThemeManager;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author mai thoa
 */
public class MyStageSingleton {

    private static MyStageSingleton instance;
    private Stage stage;

    private MyStageSingleton() {
        this.stage = new Stage();
        this.stage.setTitle("Quiz App");

    }

    public static MyStageSingleton getInstance() {
        if (instance == null) {
            instance = new MyStageSingleton();
        }

        return instance;
    }

    public void showStage(String fxml) {
        if (!this.stage.isShowing()) {
            try {
                Scene scene = new Scene(new FXMLLoader(App.class.getResource(fxml + ".fxml")).load());
                
                ThemeManager.applyTheme(scene);
                
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
