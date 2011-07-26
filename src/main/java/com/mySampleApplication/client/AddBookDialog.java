package com.mySampleApplication.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

public class AddBookDialog extends DialogBox {
    private BookAddedHandler _handler;

    public AddBookDialog(BookAddedHandler handler) {
        this._handler = handler;

        final VerticalPanel r = new VerticalPanel();

        final TextBox name = addField("Name", r);
        final TextBox genre = addField("Genre", r);
        final TextBox author = addField("Author", r);

        final HorizontalPanel panel = new HorizontalPanel();

        final Button ok = new Button("OK");
        ok.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                _handler.addBook(new MySampleApplication.Book(name.getText(), genre.getText(), author.getText()));
                hide();
            }
        });

        panel.add(ok);

        final Button cancel = new Button("Cancel");
        cancel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                hide();
            }
        });

        panel.add(cancel);

        r.add(panel);

        add(r);

    }

    private TextBox addField(final String name, final VerticalPanel r) {
        final HorizontalPanel p = new HorizontalPanel();
        p.add(new Label(name));
        final TextBox n = new TextBox();
        p.add(n);
        r.add(p);
        return n;
    }


}
