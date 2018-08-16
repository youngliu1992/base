/*
 * Copyright (c) 2015 The Jupiter Project
 *
 * Licensed under the Apache License, version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.together.knowledge.base.serializer.api.javaserializer;



import com.together.knowledge.base.serializer.api.Serializer;
import com.together.knowledge.base.serializer.api.io.InputBuf;
import com.together.knowledge.base.serializer.api.io.OutputBuf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Java自身的序列化/反序列化实现.
 *
 * jupiter
 * org.jupiter.serialization.java
 *
 * @author jiachun.fjc
 */
public class JavaSerializer extends Serializer {

    @Override
    public byte code() {
        byte code = Byte.parseByte("1");
        return code;
    }

    @Override
    public <T> OutputBuf writeObject(OutputBuf outputBuf, T obj) {
        ObjectOutputStream output = null;
        try {
            output = Outputs.getOutput(outputBuf);
            output.writeObject(obj);
            output.flush();
            return outputBuf;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException ignored) {}
            }
        }
        return null; // never get here
    }

    @Override
    public <T> byte[] writeObject(T obj) {
        //ByteArrayOutputStream buf = OutputStreams.getByteArrayOutputStream();
        ByteArrayOutputStream buf = new ByteArrayOutputStream(DEFAULT_BUF_SIZE);
        ObjectOutputStream output = null;
        try {
            output = Outputs.getOutput(buf);
            output.writeObject(obj);
            output.flush();
            return buf.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException ignored) {}
            }
            //OutputStreams.resetBuf(buf);
            buf.reset();
        }
        return null; // never get here
    }

    @Override
    public <T> T readObject(InputBuf inputBuf, Class<T> clazz) {
        ObjectInputStream input = null;
        try {
            input = Inputs.getInput(inputBuf);
            Object obj = input.readObject();
            return clazz.cast(obj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ignored) {}
            }
            inputBuf.release();
        }
        return null; // never get here
    }

    @Override
    public <T> T readObject(byte[] bytes, int offset, int length, Class<T> clazz) {
        ObjectInputStream input = null;
        try {
            input = Inputs.getInput(bytes, offset, length);
            Object obj = input.readObject();
            return clazz.cast(obj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ignored) {}
            }
        }
        return null; // never get here
    }

    @Override
    public String toString() {
        return "java:(code=" + code() + ")";
    }
}
