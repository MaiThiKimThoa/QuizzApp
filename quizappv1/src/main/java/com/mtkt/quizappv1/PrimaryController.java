package com.mtkt.quizappv1;

import com.mtkt.utils.MyAlertSingleton;
import com.mtkt.utils.themes.DarkFactory;
import com.mtkt.utils.themes.DefaultFactory;
import com.mtkt.utils.themes.LightFactory;
import com.mtkt.utils.themes.ThemeManager;
import com.mtkt.utils.themes.ThemeTypes;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.ComboBox;


public class PrimaryController implements Initializable{
    
    @FXML private ComboBox<ThemeTypes> cbThemes;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.cbThemes.setItems(FXCollections.observableArrayList(ThemeTypes.values()));
    }
    
public void mangeQuestions(ActionEvent e){
MyAlertSingleton.getInstance().showMsg("[mangeQuestions] Comming soon...");
}

public void practice(ActionEvent e){
MyAlertSingleton.getInstance().showMsg("[practice] Comming soon...");
}
public void exam(ActionEvent e){
MyAlertSingleton.getInstance().showMsg("[exam] Comming soon...");
}
   
public void changeTheme(ActionEvent e){
    this.cbThemes.getSelectionModel().getSelectedItem().updateTheme(this.cbThemes.getScene());
 

}
}
