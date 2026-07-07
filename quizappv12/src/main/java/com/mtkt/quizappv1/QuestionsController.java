package com.mtkt.quizappv1;

import com.mtkt.pojo.Category;
import com.mtkt.pojo.Choice;
import com.mtkt.pojo.Question;
import com.mtkt.pojo.Level;
import com.mtkt.services.CategoryServices;
import com.mtkt.services.LevelServices;
import com.mtkt.services.question.QuestionServices;
import com.mtkt.utils.Configs;
import com.mtkt.utils.MyAlertSingleton;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 * @author mai thoa
 */
public class QuestionsController implements Initializable {

    @FXML
    private ComboBox<Category> cbCates;
    @FXML
    private ComboBox<Level> cbLevels;
    @FXML
    private ComboBox<Category> cbSearchCates; 
    @FXML
    private ComboBox<Level> cbSearchLevels;
    @FXML
    private TableView<Question> tvQuestions;
    @FXML
    private VBox vChoices;
    @FXML
    private TextArea txtContent;
    @FXML 
    private ToggleGroup toggle;
    @FXML 
    private TextField txtKeywords;
    

    private String sql;
    private final CategoryServices categoryServices = new CategoryServices();
    private final ToggleGroup choiceGroup = new ToggleGroup();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadColumns();

        LevelServices lvlService = new LevelServices();
        try {
            // 1. Lấy và nạp danh mục lên ô thêm mới và ô tìm kiếm
            List<Category> cates = this.categoryServices.getCates();
            if (cates != null) {
                this.cbCates.setItems(FXCollections.observableArrayList(cates));
                this.cbSearchCates.setItems(FXCollections.observableArrayList(cates)); 
            }

            // 2. Lấy và nạp mức độ lên ô thêm mới và ô tìm kiếm
            List<Level> levels = null;
            if (lvlService != null) {
                levels = lvlService.getLevels();
            } else if (Configs.lvlService != null) {
                levels = Configs.lvlService.getLevels();
            }

            if (levels != null) {
                this.cbLevels.setItems(FXCollections.observableList(levels));
                this.cbSearchLevels.setItems(FXCollections.observableList(levels)); 
            }
            
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(QuestionsController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        try {
            loadTableQuestions();
        } catch (Exception e) {
            System.err.println("Lỗi load bảng câu hỏi lúc khởi tạo: " + e.getMessage());
        }
    }

    private void loadCategoriesToComboBox() {
        try {
            List<Category> cates = this.categoryServices.getCates();
            if (cates != null) {
                this.cbCates.setItems(FXCollections.observableArrayList(cates));
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi load danh mục lên ComboBox: " + e.getMessage());
        }
    }

    private void loadColumns() {
        this.tvQuestions.getColumns().clear();

        TableColumn<Question, Integer> colId = new TableColumn<>("Id");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(100);

        TableColumn<Question, String> colContent = new TableColumn<>("Nội dung câu hỏi");
        colContent.setCellValueFactory(new PropertyValueFactory<>("content"));
        colContent.setPrefWidth(300);

        this.tvQuestions.getColumns().addAll(colId, colContent);
    }

    @FXML
    public void addChoice(ActionEvent e) {
        HBox h = new HBox();
        h.getStyleClass().add("Container");

        RadioButton r = new RadioButton();
        r.setToggleGroup(choiceGroup); 
        
        TextField txt = new TextField();
        txt.getStyleClass().add("Input");

        h.getChildren().addAll(r, txt);
        this.vChoices.getChildren().add(h);
    }

    @FXML
    public void addQuestion(ActionEvent e) {
        if (this.txtContent.getText() == null || this.txtContent.getText().trim().isEmpty()) {
            MyAlertSingleton.getInstance().showMsg("Nội dung câu hỏi không được trống!", Alert.AlertType.WARNING);
            return;
        }

        Question q = new Question(
            0,
            this.txtContent.getText().trim(),
            this.cbCates.getSelectionModel().getSelectedItem(),
            this.cbLevels.getSelectionModel().getSelectedItem()
        );

        List<Choice> choices = new ArrayList<>();
        for (var node : this.vChoices.getChildren()) {
            if (node instanceof HBox) {
                HBox h = (HBox) node;
                if (h.getChildren().size() >= 2 
                        && h.getChildren().get(0) instanceof RadioButton 
                        && h.getChildren().get(1) instanceof TextField) {
                    
                    RadioButton rdo = (RadioButton) h.getChildren().get(0);
                    TextField txt = (TextField) h.getChildren().get(1);

                    choices.add(new Choice(txt.getText().trim(), rdo.isSelected()));
                }
            }
        }
        
        try {
            Optional<ButtonType> b = MyAlertSingleton.getInstance().showMsg("Bạn chắc chắn thêm không?", Alert.AlertType.CONFIRMATION);
            if (b.isPresent() && b.get() == ButtonType.OK) {
                if (Configs.uQuestionService != null) {
                    Configs.uQuestionService.addQuestion(q, choices);
                } else {
                    QuestionServices s2 = new QuestionServices();
                    s2.addQuestion(q, choices); 
                }
                
                MyAlertSingleton.getInstance().showMsg("Thêm câu hỏi thành công! ");
                this.txtContent.clear();
                this.vChoices.getChildren().clear();
                loadTableQuestions();
            }
            
        } catch (Exception ex) { 
            MyAlertSingleton.getInstance().showMsg("Thêm câu hỏi thất bại: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
   
    
    private void loadTableQuestions() {
        try {
            String kw = this.txtKeywords != null ? this.txtKeywords.getText() : "";
            Category cate = this.cbSearchCates.getSelectionModel().getSelectedItem();
            Level lvl = this.cbSearchLevels.getSelectionModel().getSelectedItem();

            if (Configs.questionService != null) {
                this.tvQuestions.setItems(FXCollections.observableList(
                    Configs.questionService.getQuestions(kw, cate, lvl)
                ));
            } else {
                QuestionServices s2 = new QuestionServices();
                this.tvQuestions.setItems(FXCollections.observableList(
                    s2.getQuestions(kw, cate, lvl)
                ));
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(QuestionsController.class.getName())
                                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}