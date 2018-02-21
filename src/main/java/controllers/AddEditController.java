package controllers;

import common.*;
import common.ScreenLoader;
import domain.BaseEntity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import viewmodels.BaseViewModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;

public abstract class AddEditController<TViewModel extends BaseViewModel, TModel extends BaseEntity> extends BaseController<TViewModel> {

    protected abstract void saveAction(TModel item);
    protected abstract TModel getModelItem();
    protected abstract ArrayList<String> getValiddationMessages(TModel part);

    protected void setNumberFormat(TextField textField){

        DecimalFormat format = new DecimalFormat( );
        format.setGroupingUsed(false);

        textField.setTextFormatter( new TextFormatter<>(c ->
        {
            String newText = c.getControlNewText();

            if ( newText == null || newText.isEmpty()) {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(newText, parsePosition);

            if (object == null || parsePosition.getIndex() < newText.length()) {
                return null;
            } else {
                return c;
            }
        }));
    }

    protected final boolean validate(TModel item) throws ValidationException {
        ArrayList<String> messages = getValiddationMessages(item);
        if (messages.size() == 0){
            return true;
        }

        throw new ValidationException(messages);
    }

    protected void setMoneyValidation(TextField txtField) {

        //the call to set number format won't work with text fields bound to currency
        //because of the leading $ char. Binding prevents invalid chars
        //but this has to be handled differently here
        txtField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                // this isn't working because if backspace will not remove the first char
                //after $ if those are the only chars in the string.
//                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
//                try{
//                    currencyFormatter.parse(newValue);
//                    return;
//                }
//                catch (ParseException ex){
//                    if (newValue == "$"){
//                        return;
//                    }
//                    txtField.setText(oldValue);
//                }

                //the above commented code is preferable
                //because it's possible to enter in invalid values
                //such as $0.0.....5...6
                //but that type of invalid string will have to be handled elsewhere for now
                for (int i =0; i < newValue.length(); i++){
                    Character c = newValue.charAt(i);
                    if (c == '$' || c == '.'){
                        continue;
                    }
                    else if (!Character.isDigit(c)){
                        txtField.setText(oldValue);
                        return;
                    }
                }
            }
        });
    }

    protected double getMoneyValue(TextField txtField) throws ParseException {

        //making sure that every value starts with a $ and has a number in it
        String value = txtField.getText();
        if (value == null || value.isEmpty()){
            value = "$0.00";
        }
        if (value.charAt(0) != '$'){
            value = "$" + value;
        }
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        Number number = currencyFormatter.parse(value);
        return number.doubleValue();
    }

    protected void setCurrencyTextBox(TextField txtField, double price){
        Double money = price;

        txtField.setText("$" + money.toString());
    }

    @FXML
    public void save(ActionEvent event) throws ValidationException {

        TModel item = getModelItem();

        if (validate(item)){
            saveAction(item);

            ScreenLoader loader = new ScreenLoader();
            loader.load(event, Configuration.Paths.get_MainPath(), WindowSize.Large);
        }
    }

    @FXML
    public void cancel(ActionEvent event){
        String message = "Are you sure you want to cancel? All changes will be lost!";

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            ScreenLoader loader = new ScreenLoader();
            loader.load(event, Configuration.Paths.get_MainPath(), WindowSize.Large);

        }
    }

}
