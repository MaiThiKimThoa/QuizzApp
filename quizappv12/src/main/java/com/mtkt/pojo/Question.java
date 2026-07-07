package com.mtkt.pojo;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author mai thoa
 */
public class Question {
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final StringProperty content = new SimpleStringProperty(this, "content");
    private final StringProperty hint = new SimpleStringProperty(this, "hint");
    private final StringProperty image = new SimpleStringProperty(this, "image");
    private final ObjectProperty<Category> category = new SimpleObjectProperty<>(this, "category");
    private final ObjectProperty<Level> level = new SimpleObjectProperty<>(this, "level");
    private List<Choice> choices = new ArrayList<>();

    // Constructor mặc định
    public Question() {
    }

    // Constructor chuẩn đầy đủ tham số
    public Question(int id, String content, Category category, Level level) {
        setId(id);
        setContent(content);
        setCategory(category);
        setLevel(level);
    }

    // Hàm phụ trợ cho code cũ
    public Category getCate() {
        return getCategory();
    }

    // --- ID PROPERTY, GETTER, SETTER ---
    public int getId() {
        return id.get();
    }

    public void setId(int value) {
        id.set(value);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    // --- CONTENT PROPERTY, GETTER, SETTER ---
    public String getContent() {
        return content.get();
    }

    public void setContent(String value) {
        content.set(value);
    }

    public StringProperty contentProperty() {
        return content;
    }

    // --- HINT PROPERTY, GETTER, SETTER ---
    public String getHint() {
        return hint.get();
    }

    public void setHint(String value) {
        hint.set(value);
    }

    public StringProperty hintProperty() {
        return hint;
    }

    // --- IMAGE PROPERTY, GETTER, SETTER ---
    public String getImage() {
        return image.get();
    }

    public void setImage(String value) {
        image.set(value);
    }

    public StringProperty imageProperty() {
        return image;
    }

    // --- CATEGORY PROPERTY, GETTER, SETTER ---
    public Category getCategory() {
        return category.get();
    }

    public void setCategory(Category value) {
        category.set(value);
    }

    public ObjectProperty<Category> categoryProperty() {
        return category;
    }

    // --- LEVEL PROPERTY, GETTER, SETTER ---
    public Level getLevel() {
        return level.get();
    }

    public void setLevel(Level value) {
        level.set(value);
    }

    public ObjectProperty<Level> levelProperty() {
        return level;
    }

    // --- CHOICES LIST ---
    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}