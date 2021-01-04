package org.houjun.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 自定义的消息通道
 * 接收通道和发送通道定义在一个接口中，消费者和生产者身份集一身
 */
public interface MyChannel {
    static final String INPUT = "houjun-input";
    static final String OUTPUT = "houjun-output";

    @Output(OUTPUT)
    MessageChannel output();

    @Input(INPUT)
    SubscribableChannel input();
}
