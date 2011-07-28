package com.mySampleApplication.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

public interface PollerServiceAsync {
    void letThereBeBooks(AsyncCallback<Void> async);
}
