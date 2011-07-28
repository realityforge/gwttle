package com.mySampleApplication.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("Poller")
public interface PollerService extends RemoteService {
    void letThereBeBooks();
}
