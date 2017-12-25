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

import java.util.List;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * MessageListenerConcurrently用来并发接收异步收到的消息
 * 无序的 不追求时间顺序，只要把生产出来的事件全部消费完就可以。这种可以用并行的方式处理，效率高很多
 */
public interface MessageListenerConcurrently extends MessageListener {
    /**
     * 不推荐抛出异常，消费失败了推荐返回ConsumeConcurrentlyStatus.RECONSUME_LATER
     *
     * @param msgs msgs.size() >= 1 默认消息数为1
     * @return 消费状态
     */
    ConsumeConcurrentlyStatus consumeMessage(final List<MessageExt> msgs,
        final ConsumeConcurrentlyContext context);
}
