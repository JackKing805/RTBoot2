package com.rtboot.boot.http.i;

import java.io.OutputStream;

public interface ResponseWriteListener {
    void write(OutputStream outputStream) throws Exception;
}
