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
package org.apache.rocketmq.store;

public enum PutMessageStatus {
    /**
     * 储存成功
     */
    PUT_OK,
    /**
     * flush磁盘超时
     */
    FLUSH_DISK_TIMEOUT,
    /**
     * flush slave超时
     */
    FLUSH_SLAVE_TIMEOUT,
    /**
     * slave不可用
     */
    SLAVE_NOT_AVAILABLE,
    /**
     * 服务不可用
     */
    SERVICE_NOT_AVAILABLE,
    /**
     * 创建映射文件失败
     */
    CREATE_MAPEDFILE_FAILED,
    /**
     * 非法消息
     */
    MESSAGE_ILLEGAL,
    /**
     * properties大小超过最大长度
     */
    PROPERTIES_SIZE_EXCEEDED,
    /**
     * 系统page缓存超时
     */
    OS_PAGECACHE_BUSY,
    /**
     * 未知异常
     */
    UNKNOWN_ERROR,
}
