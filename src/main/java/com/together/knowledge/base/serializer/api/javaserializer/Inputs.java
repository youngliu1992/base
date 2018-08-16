package com.together.knowledge.base.serializer.api.javaserializer;

import com.together.knowledge.base.serializer.api.io.InputBuf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Inputs {
    public static ObjectInputStream getInput(InputBuf inputBuf) throws IOException {
        return new ObjectInputStream(inputBuf.inputStream());
    }

    public static ObjectInputStream getInput(byte[] bytes, int offset, int length) throws IOException {
        return new ObjectInputStream(new ByteArrayInputStream(bytes, offset, length));
    }

    private Inputs() {}
}
