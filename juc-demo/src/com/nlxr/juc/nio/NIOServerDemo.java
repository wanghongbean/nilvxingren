package com.nlxr.juc.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author labu
 * @Date 2021/3/8
 * @Description
 */
public class NIOServerDemo {
    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Selector selector = Selector.open();//静态工厂方法创建一个选择器
        //创建一个服务端channel，绑定一个sockey对象，并把这个通信信道注册到选择器上，设置为非阻塞模式
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);//非阻塞模式
        ssc.socket().bind(new InetSocketAddress(8888));
        ssc.register(selector, SelectionKey.OP_ACCEPT);//注册监听事件
        while (true){
            //检查已经注册在这个选择器上的所有通信信道是否有需要的事件发生，
            // 如果有某个事件发生就返回所有的selectionkey，通过这个对象的channel方法就可以取得这通信信道对象，
            // 从而读取通信的数据，读取到buffer中，buffer是我们可以控制的缓冲器
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectionKeys.iterator();
            while (it.hasNext()){
                SelectionKey key = it.next();
                //demo中是把监听连接请求的事件和处理请求的事件放在一个线程中，在事件应用中，通常把他们放到两个线程中：
                //一个专门负责监听客户端的连接请求，而且是阻塞的方式执行；一个专门负责处理请求，采用NIO的方式，如Tomcat，jetty
                if ((key.readyOps()&SelectionKey.OP_ACCEPT)==SelectionKey.OP_ACCEPT){
                    ServerSocketChannel ssChannel = (ServerSocketChannel)key.channel();
                    SocketChannel sc = ssChannel.accept();//接收到服务服务端请求
                    sc.configureBlocking(false);
                    sc.register(selector,SelectionKey.OP_READ);
                    it.remove();
                }else if ((key.readyOps()&SelectionKey.OP_READ) == SelectionKey.OP_READ){
                    SocketChannel channel = (SocketChannel)key.channel();
                    while (true){
                        buffer.clear();
                        int n = channel.read(buffer);
                        if (n<=0){
                            break;
                        }
                        buffer.flip();
                    }
                    it.remove();
                }
            }

        }
    }

    @Test
    public void test_num(){
        System.out.println(SelectionKey.OP_READ);
        System.out.println(SelectionKey.OP_ACCEPT);
        System.out.println(SelectionKey.OP_WRITE);
        System.out.println(SelectionKey.OP_CONNECT);
    }
}
