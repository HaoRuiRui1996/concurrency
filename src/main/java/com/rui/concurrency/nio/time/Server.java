package com.rui.concurrency.nio.time;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));
        serverSocketChannel.configureBlocking(false);   //设置为非阻塞模式
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); //套接字接受操作


        //不创建新线程来异步执行了
        while (true) {
            selector.select(1000);
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            Iterator it = selectionKeySet.iterator();
            while (it.hasNext()) {
                SelectionKey selectionKey = (SelectionKey) it.next();
                //如果不移除键的话，下一次还会返回
                it.remove();
                if (selectionKey.isValid()) {
                    if (selectionKey.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isReadable()) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        System.out.println("read......");
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        try {
                            int read = 0;
                            try {
                                read = socketChannel.read(byteBuffer);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            StringBuffer sb = new StringBuffer();
                            if (read > 0) {
                                byteBuffer.flip();//切换为写模式
                                byte[] temp = new byte[byteBuffer.remaining()];
                                byteBuffer.get(temp);
                                sb.append(new String(temp));
                            } else if (read < 0) {
                                //对端链路关闭
                                socketChannel.close();
                            } else {
                                //读到0字节 忽略
                                ;
                            }
                            System.out.println(sb);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
    }

}
