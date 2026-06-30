/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mtkt.utils.themes;

import com.mtkt.quizappv1.App;
import javafx.scene.Scene;

/**
 *
 * @author mai thoa
 */
public class ThemeManager {
    private static ThemeAbstractFactory factory = new DefaultFactory();

    /**
     * @param aFactory the factory to set
     */
    public static void setFactory(ThemeAbstractFactory aFactory) {
        factory = aFactory;
    }
    
    public static void applyTheme(Scene scene){
    scene.getRoot().getStylesheets().clear();
         scene.getRoot().getStylesheets().add(factory.getStyleSheet());
    }
    
}
