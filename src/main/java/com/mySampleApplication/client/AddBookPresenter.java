package com.mySampleApplication.client;

import com.google.gwt.user.client.ui.HasValue;
import com.mySampleApplication.shared.Book;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

public class AddBookPresenter extends WidgetPresenter<AddBookPresenter.Display> {
    public interface CancelListener {
        void cancel();
    }

    public interface HasCancelListener {
        void addCancelListener(CancelListener listener);
    }

    public interface OkListener {
        void ok();
    }

    public interface HasOkListener {
        void addOkListener(OkListener listener);
    }


    public interface Display extends WidgetDisplay {
        HasValue<String> getName();

        HasValue<String> getGenre();

        HasValue<String> getAuthor();

        HasOkListener getOkButton();

        HasCancelListener getCancelButton();

        void open();

        void close();
    }

    public AddBookPresenter(Display display, EventBus eventBus) {
        super(display, eventBus);
    }

    @Override
    protected void onBind() {
        getDisplay().getOkButton().addOkListener(new OkListener() {
            @Override
            public void ok() {
                final Book book = new Book(null,
                        getDisplay().getName().getValue(),
                        getDisplay().getGenre().getValue(),
                        getDisplay().getAuthor().getValue());
                eventBus.fireEvent(new RequestBookAddEvent(book));
                getDisplay().close();
            }
        });

        getDisplay().getCancelButton().addCancelListener(new CancelListener() {
            @Override
            public void cancel() {
                getDisplay().close();
            }
        });
        getDisplay().open();
    }

    @Override
    protected void onUnbind() {

    }

    @Override
    protected void onRevealDisplay() {

    }
}
