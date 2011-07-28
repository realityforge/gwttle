package com.mySampleApplication.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class AddBookDialog
        extends DialogBox
        implements AddBookPresenter.Display, AddBookPresenter.HasCancelListener, AddBookPresenter.HasOkListener {

    private AddBookPresenter.OkListener okListener;
    private AddBookPresenter.CancelListener cancelListener;

    interface AddBookDialogUIBinder extends UiBinder<Widget, AddBookDialog> {
    }

    private static AddBookDialogUIBinder uiBinder = GWT.create(AddBookDialogUIBinder.class);

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

    public AddBookDialog() {
        setWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("saveNewButton")
    public void onOkClick(ClickEvent event) {
        okListener.ok();
    }

    @UiHandler("cancelNewButton")
    public void onClick(ClickEvent event) {
        cancelListener.cancel();
    }

    @Override
    public HasValue<String> getName() {
        return nameField;
    }

    @Override
    public HasValue<String> getGenre() {
        return new HasValue<String>() {
            @Override
            public String getValue() {
                return genreField.getItemText(genreField.getSelectedIndex());
            }

            @Override
            public void setValue(String value) {
                throw new IllegalStateException();
            }

            @Override
            public void setValue(String value, boolean fireEvents) {
                throw new IllegalStateException();
            }

            @Override
            public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> stringValueChangeHandler) {
                throw new IllegalStateException();
            }

            @Override
            public void fireEvent(GwtEvent<?> event) {
                throw new IllegalStateException();
            }
        };
    }

    @Override
    public void open() {
        center();
    }

    @Override
    public void close() {
        hide();
    }

    @Override
    public HasValue<String> getAuthor() {
        return authorField;
    }

    @Override
    public AddBookPresenter.HasOkListener getOkButton() {
        return this;
    }

    @Override
    public AddBookPresenter.HasCancelListener getCancelButton() {
        return this;
    }

    @Override
    public void addCancelListener(AddBookPresenter.CancelListener listener) {
        cancelListener = listener;
    }

    @Override
    public void addOkListener(AddBookPresenter.OkListener listener) {
        okListener = listener;
    }
}
