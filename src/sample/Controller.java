package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.*;

public class Controller {

    private static String alphabet = "!@#$%^&*()`_+{}:|/<>№;%*-=+~?., abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЬЫЪЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static String keyString;
    private static String message;
    private int index;

    @FXML
    private TextArea text;

    @FXML
    private Button encrypt;

    @FXML
    private Button decrypt;

    @FXML
    private TextField keyField;

    @FXML
    void initialize() {
        encrypt.setOnAction(event -> {
            StringBuilder encode = new StringBuilder();
            message = text.getText();
            keyString = keyField.getText();
            index = 0;
            if (keyString.length() != 0) {
                for (char symbol : message.toCharArray()) {
                    if (index == keyString.toCharArray().length) {
                        index = 0;
                    }
                    encode.append(alphabet.charAt((alphabet.indexOf(symbol) + alphabet.indexOf(keyString.charAt(index))) % alphabet.length()));
                    index++;
                }
                text.setText(encode.toString());
            } else {
                alert();
            }
        });

        decrypt.setOnAction(event -> {
            StringBuilder decode = new StringBuilder();
            message = text.getText();
            keyString = keyField.getText();
            index = 0;
            if (keyString.length() != 0) {
                for (char symbol : message.toCharArray()) {
                    if (index == keyString.toCharArray().length) {
                        index = 0;
                    }
                    decode.append(alphabet.charAt(((alphabet.indexOf(symbol) + 160) - alphabet.indexOf(keyString.charAt(index))) % alphabet.length()));
                    index++;
                }
                text.setText(decode.toString());
            } else {
                alert();
            }
        });
    }

    @FXML
    void open() {
        FileChooser fileChooser = new FileChooser();
        File fileSelected = fileChooser.showOpenDialog(null);
        StringBuilder text1 = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileSelected), "Cp1251"));
            String line;
            while ((line = br.readLine()) != null) {
                text1.append(line);
            }
            br.close();
            text.setText(text1.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void save() {
        FileChooser fileChooser = new FileChooser();
        File fileSave = fileChooser.showSaveDialog(null);
        if (fileSave != null) {
            try {
                Writer out = new OutputStreamWriter(new FileOutputStream(fileSave), "Cp1251");
                out.write(text.getText());
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void alert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Key field cannot be empty");
        alert.setContentText("Enter the key");
        alert.showAndWait();
    }
}

