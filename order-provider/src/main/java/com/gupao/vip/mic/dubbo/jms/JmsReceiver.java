package com.gupao.vip.mic.dubbo.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class JmsReceiver {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("" +
                "tcp://192.168.159.136:61616");
        Connection connection = null;
        try {
            //创建连接
            connection = connectionFactory.createConnection();
            connection.start();
            //第一个参数  Boolean.TRUE  表示的是当前连接的会话是不是一个事务性的会话。
            Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);

            //创建队列（如果队列已经存在则不会创建， first-queue是队列名称）
            //destination表示目的地
            Destination destination = session.createQueue("first-queue");
            //创建消息接收者
            MessageConsumer consumer = session.createConsumer(destination);

            TextMessage textMessage = (TextMessage) consumer.receive();
            textMessage.acknowledge();

            System.out.println(textMessage.getStringProperty("key"));

            System.out.println(textMessage.getText());
            //session.commit();   //为什么这里的接收端也需要commit呢,因为就像自己要进行邮件的签收一样,告诉快递员自己已经收到了，然后才会从消息队列当中去除这条消息
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}