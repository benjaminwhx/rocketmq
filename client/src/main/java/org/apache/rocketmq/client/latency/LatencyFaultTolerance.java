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

package org.apache.rocketmq.client.latency;

/**
 * 延迟故障容错
 * @param <T>
 */
public interface LatencyFaultTolerance<T> {
    /**
     * 更新对应的延迟和不可用时长
     * @param name brokerName
     * @param currentLatency 延迟时间
     * @param notAvailableDuration 不可用时间
     */
    void updateFaultItem(final T name, final long currentLatency, final long notAvailableDuration);

    /**
     * 对象是否可用
     * @param name 条目名称
     * @return
     */
    boolean isAvailable(final T name);

    /**
     * 移除对象
     * @param name
     */
    void remove(final T name);

    /**
     * 随机挑选一个 选最低延迟的broker,根据FaultItem的可用性和开始时间选择
     * @return
     */
    T pickOneAtLeast();
}
