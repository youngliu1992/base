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

package com.together.knowledge.base.serializer.api.hessianserializer.io;

import com.caucho.hessian.io.Hessian2Output;
import com.together.knowledge.base.serializer.api.io.OutputBuf;

import java.io.OutputStream;

/**
 * jupiter
 * org.jupiter.serialization.hessian.io
 *
 * @author jiachun.fjc
 */
public final class Outputs {

    public static Hessian2Output getOutput(OutputBuf outputBuf) {
        return new Hessian2Output(outputBuf.outputStream());
    }

    public static Hessian2Output getOutput(OutputStream buf) {
        return new Hessian2Output(buf);
    }

    private Outputs() {}
}
