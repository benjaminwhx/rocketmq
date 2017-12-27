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
package org.apache.rocketmq.client.consumer.listener;

import org.apache.rocketmq.common.message.MessageQueue;

/**
 * 顺序消费上下文
 */
public class ConsumeOrderlyContext {
    private final MessageQueue messageQueue;
    /**
     * 当SUSPEND_CURRENT_QUEUE_A_MOMENT时，autoCommit设置为true或者false没有区别，本质跟消费相反，把消息从msgTreeMapTemp转移回msgTreeMap，等待下次消费。

     当SUCCESS时，autoCommit设置为true时比设置为false多做了2个动作
     consumeRequest.getProcessQueue().commit() 和 this.defaultMQPushConsumerImpl.getOffsetStore().updateOffset(consumeRequest.getMessageQueue(), commitOffset, false);
     ProcessQueue.commit() ：
     本质是删除msgTreeMapTemp里的消息，msgTreeMapTemp里的消息在上面消费时从msgTreeMap转移过来的。
     this.defaultMQPushConsumerImpl.getOffsetStore().updateOffset() ：本质是把拉消息的偏移量更新到本地，然后定时更新到broker。

     那么少了这2个动作会怎么样呢，随着消息的消费进行，msgTreeMapTemp里的消息堆积越来越多，消费消息的偏移量一直没有更新到broker导致consumer每次重新启动后都要从头开始重复消费。
     就算更新了offset到broker，那么msgTreeMapTemp里的消息堆积呢？不知道这算不算bug。
     所以，还是把autoCommit设置为true吧。
     */
    private boolean autoCommit = true;
    private long suspendCurrentQueueTimeMillis = -1;

    public ConsumeOrderlyContext(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public MessageQueue getMessageQueue() {
        return messageQueue;
    }

    public long getSuspendCurrentQueueTimeMillis() {
        return suspendCurrentQueueTimeMillis;
    }

    public void setSuspendCurrentQueueTimeMillis(long suspendCurrentQueueTimeMillis) {
        this.suspendCurrentQueueTimeMillis = suspendCurrentQueueTimeMillis;
    }
}
