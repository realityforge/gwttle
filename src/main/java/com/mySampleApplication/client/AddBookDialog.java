package com.mySampleApplication.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class AddBookDialog extends DialogBox {

    interface AddBookDialogUIBinder extends UiBinder<Widget, AddBookDialog> {
    }

    private static AddBookDialogUIBinder uiBinder = GWT.create(AddBookDialogUIBinder.class);

    private BookAddedHandler _handler;
    @UiField
    TextBox nameField;
    @UiField
    ListBox genreField;
    @UiField
    TextBox authorField;
    @UiField
    Button saveNewButton;
    @UiField
    Button cancelNewButton;

    public AddBookDialog(BookAddedHandler handler) {
        this._handler = handler;
        setWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("saveNewButton")
    public void onOkClick(ClickEvent event) {
        _handler.addBook(new MySampleApplication.Book(nameField.getText(), genreField.getItemText(genreField.getSelectedIndex()), authorField.getText()));
        hide();
    }

    @UiHandler("cancelNewButton")
    public void onClick(ClickEvent event) {
        hide();
    }
}
