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

package com.together.knowledge.base.thread.util;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Formatter;

/**
 * Jupiter constants.
 *
 * jupiter
 * org.jupiter.common.util
 *
 * @author jiachun.fjc
 */
public final class JConstants {

    /** 换行符 */
    public static final String NEWLINE;
    /** 字符编码 */
    public static final String UTF8_CHARSET = "UTF-8";
    public static final Charset UTF8;
    static {
        String newLine;
        try {
            newLine = new Formatter().format("%n").toString();
        } catch (Exception e) {
            newLine = "\n";
        }
        NEWLINE = newLine;

        Charset charset = null;
        try {
            charset = Charset.forName(UTF8_CHARSET);
        } catch (UnsupportedCharsetException ignored) {}
        UTF8 = charset;
    }

    /** Cpu核心数 */
    public static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    /** 未知应用名称 */
    public static final String UNKNOWN_APP_NAME = "UNKNOWN";
    /** 服务默认组别 */
    public static final String DEFAULT_GROUP = "Jupiter";
    /** 服务默认版本号 */
    public static final String DEFAULT_VERSION = "1.0.0";


    private JConstants() {}
}
