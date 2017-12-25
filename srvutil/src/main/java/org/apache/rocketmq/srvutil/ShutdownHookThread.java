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

package org.apache.rocketmq.srvutil;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;

/**
 * {@link ShutdownHookThread}是一个标准的钩子类，提供给filtersrv和namesrv模块去使用
 * 通过{@link Callable}接口，钩子可以在任何地方自定义操作
 */
public class ShutdownHookThread extends Thread {
    // 有没有关闭
    private volatile boolean hasShutdown = false;
    // 关闭次数
    private AtomicInteger shutdownTimes = new AtomicInteger(0);
    private final Logger log;
    private final Callable callback;

    /**
     * 使用Callable回调创建实例
     *
     * @param log slf4j实例
     * @param callback callback回调
     */
    public ShutdownHookThread(Logger log, Callable callback) {
        super("ShutdownHook");
        this.log = log;
        this.callback = callback;
    }

    /**
     * 当jvm关闭时被调用
     * 1. 统计调用次数
     * 2. 执行 {@link ShutdownHookThread#callback} 并统计执行时间
     */
    @Override
    public void run() {
        synchronized (this) {
            log.info("shutdown hook was invoked, " + this.shutdownTimes.incrementAndGet() + " times.");
            if (!this.hasShutdown) {
                this.hasShutdown = true;
                long beginTime = System.currentTimeMillis();
                try {
                    this.callback.call();
                } catch (Exception e) {
                    log.error("shutdown hook callback invoked failure.", e);
                }
                long consumingTimeTotal = System.currentTimeMillis() - beginTime;
                log.info("shutdown hook done, consuming time total(ms): " + consumingTimeTotal);
            }
        }
    }
}
