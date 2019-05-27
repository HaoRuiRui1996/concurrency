package com.rui.concurrency.nio.time;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Client {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        if (socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080))) {
            System.out.println("connected");
            byte[] req = "QUERY TIME ORDER".getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
            writeBuffer.put(req);
            writeBuffer.flip();
            System.out.println(writeBuffer.remaining());
            socketChannel.write(writeBuffer);
            if (!writeBuffer.hasRemaining()) {
                System.out.println("Send order 2 server succeed.");
            }
        } else {
            System.out.println("connect failed");
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            while (true) {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();
                    SocketChannel sc = (SocketChannel) key.channel();
                    if (key.isConnectable()) {
                        if (sc.finishConnect()) {
                            System.out.println("connected");
                            byte[] req = "QUERY TIME ORDER".getBytes();
                            ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
                            writeBuffer.put(req);
                            writeBuffer.flip();
                            System.out.println(writeBuffer.remaining());
                            sc.write(writeBuffer);
                        }
                    }
                }
            }
        }
        socketChannel.close();

    }
}
