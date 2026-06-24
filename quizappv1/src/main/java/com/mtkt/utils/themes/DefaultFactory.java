/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mtkt.utils.themes;

import com.mtkt.quizappv1.App;

/**
 *
 * @author mai thoa
 */
public class DefaultFactory extends ThemeAbstractFactory {
    
    @Override
    public String getStyleSheet(){
   return App.class.getResource("styles.css").toExternalForm();
    }
}
