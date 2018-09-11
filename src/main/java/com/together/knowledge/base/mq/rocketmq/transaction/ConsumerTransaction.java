package com.together.knowledge.base.mq.rocketmq.transaction;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ConsumerTransaction {

    public ConsumerTransaction() {
        String group_name = "transaction_consumer";
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group_name);
        consumer.setNamesrvAddr("127.0.0.1:9876");
        try {
            consumer.subscribe("TopicTest", "*");
            consumer.registerMessageListener(new Listener());
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    class Listener implements MessageListenerConcurrently {
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
            try {
                for(MessageExt msg : list) {
                    String topic = msg.getTopic();
                    String msgBody = new String(msg.getBody(), "utf-8");
                    String tags = msg.getTags();
                    System.out.println("收到消息:" + "topic:" + topic + ", tags:" + tags + ",msg:" + msgBody);
                    msg.getTags();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }

    public static void main(String[] args) {
        ConsumerTransaction c = new ConsumerTransaction();
        System.out.println("transaction consumer start......");
    }


}
