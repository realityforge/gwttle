package com.mySampleApplication.shared;

import java.io.Serializable;

public interface Action<T extends Response> extends Serializable {
    T execute(LibraryService service);
}
