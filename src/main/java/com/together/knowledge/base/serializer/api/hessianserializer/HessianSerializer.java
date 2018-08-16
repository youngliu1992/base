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

package com.together.knowledge.base.serializer.api.hessianserializer;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.together.knowledge.base.serializer.api.Serializer;
import com.together.knowledge.base.serializer.api.hessianserializer.io.Inputs;
import com.together.knowledge.base.serializer.api.hessianserializer.io.Outputs;
import com.together.knowledge.base.serializer.api.io.InputBuf;
import com.together.knowledge.base.serializer.api.io.OutputBuf;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Hessian的序列化/反序列化实现
 *
 * jupiter
 * org.jupiter.serialization.hessian
 *
 * @author jiachun.fjc
 */
public class HessianSerializer extends Serializer {

    @Override
    public byte code() {
        byte code = Byte.parseByte("1");
        return code;
    }

    @Override
    public <T> OutputBuf writeObject(OutputBuf outputBuf, T obj) {
        Hessian2Output output = Outputs.getOutput(outputBuf);
        try {
            output.writeObject(obj);
            output.flush();
            return outputBuf;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException ignored) {}
        }
        return null; // never get here
    }

    @Override
    public <T> byte[] writeObject(T obj) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream(DEFAULT_BUF_SIZE);;
        Hessian2Output output = Outputs.getOutput(buf);
        try {
            output.writeObject(obj);
            output.flush();
            return buf.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException ignored) {}

            buf.reset();
        }
        return null; // never get here
    }

    @Override
    public <T> T readObject(InputBuf inputBuf, Class<T> clazz) {
        Hessian2Input input = Inputs.getInput(inputBuf);
        try {
            Object obj = input.readObject(clazz);
            return clazz.cast(obj);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException ignored) {}

            inputBuf.release();
        }
        return null; // never get here
    }

    @Override
    public <T> T readObject(byte[] bytes, int offset, int length, Class<T> clazz) {
        Hessian2Input input = Inputs.getInput(bytes, offset, length);
        try {
            Object obj = input.readObject(clazz);
            return clazz.cast(obj);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException ignored) {}
        }
        return null; // never get here
    }

    @Override
    public String toString() {
        return "hessian:(code=" + code() + ")";
    }
}
