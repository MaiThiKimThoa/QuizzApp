package com.mtkt.pojo;

/**
 * @author mai thoa
 */
public class Choice {
    private int id;
    private String content;
    private boolean correct;

    public Choice() {
    }

    public Choice(int id, String content, boolean correct) {
        this.id = id;
        this.content = content;
        this.correct = correct;
    }
    
    public Choice(String content, boolean correct) {
        this.content = content;
        this.correct = correct;
    }
    
    // --- GETTERS AND SETTERS ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    // Ham phu tro cho JavaFX PropertyValueFactory va code cu
    public boolean isIsCorrect() {
        return correct; 
    }
}