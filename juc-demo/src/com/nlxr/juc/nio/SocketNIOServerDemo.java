package com.nlxr.juc.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @Author labu
 * @Date 2021/3/9
 * @Description
 */
public class SocketNIOServerDemo {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        ssChannel.configureBlocking(false);
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);

        ServerSocket socket = ssChannel.socket();
        socket.bind(new InetSocketAddress("127.0.0.1",8888));

        while (true){
            if (selector.select(100) == 0){
                continue;
            }
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()){
                SelectionKey key = it.next();
                it.remove();
                if (key.isValid() && key.isAcceptable()){
                    ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    sc.register(selector,SelectionKey.OP_READ);
                }else if (key.isValid() && key.isReadable()){
                    SocketChannel sc = (SocketChannel)key.channel();
                    String data = readDataFromSocketChannel(sc);
                    System.out.println("read from client : "+ data);
                    sc.close();
                }
            }
        }

    }

    public static String readDataFromSocketChannel(SocketChannel sc) throws IOException {
        StringBuilder sb = new StringBuilder();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true){
            buffer.clear();
            int read = sc.read(buffer);
            if (read <=0 ){
                break;
            }
            buffer.flip();
            int limit = buffer.limit();
            char[] dst = new char[limit];
            for (int i = 0; i < limit; i++) {
                dst[i] = (char)buffer.get(i);
            }
            sb.append(dst);
            buffer.clear();
        }
        return sb.toString();
    }
}
