/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.rocketmq.remoting.common;

public enum SslMode {

    /**
     * 失效
     * 不支持SSL，任何传入的SSL握手都将被拒绝，导致连接关闭。
     */
    DISABLED("disabled"),
    /**
     * 许可，SSL是可选的，这种模式说明连接的时候SSL是可选的。
     */
    PERMISSIVE("permissive"),
    /**
     * 强制，SSL是必选的，没有SSL的连接将被拒绝
     */
    ENFORCING("enforcing");

    private String name;

    SslMode(String name) {
        this.name = name;
    }

    public static SslMode parse(String mode) {
        for (SslMode sslMode: SslMode.values()) {
            if (sslMode.name.equals(mode)) {
                return sslMode;
            }
        }

        return PERMISSIVE;
    }

    public String getName() {
        return name;
    }
}
