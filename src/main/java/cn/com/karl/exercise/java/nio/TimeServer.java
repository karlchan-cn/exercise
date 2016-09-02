package cn.com.karl.exercise.java.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Pattern;

/*
 * @(#)TimeServer.java	1.3 01/12/13
 * Listen for connections and tell callers what time it is.  
 * Demonstrates NIO socket channels (accepting and writing), 
 * buffer handling, charsets, and regular expressions.
 *
 * Copyright (c) 2001, 2002, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * Redistribution and use in source and binary forms, with or 
 * without modification, are permitted provided that the following 
 * conditions are met:
 *
 * -Redistributions of source code must retain the above copyright
 * 
 * -Redistributions of source code must retain the above copyright  
 * notice, this  list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduct the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * 
 * -Redistribution in binary form must reproduct the above copyright 
 * notice, this list of conditions and the following disclaimer in 
 * the documentation and/or other materials provided with the 
 * distribution.
 *
 * Neither the name of Oracle nor the names of
 * contributors may be used to endorse or promote products derived
 * 
 * Neither the name of Oracle nor the names of 
 * contributors may be used to endorse or promote products derived 
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES  SUFFERED BY LICENSEE AS A RESULT OF  OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THE SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF
 * THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN
 * 
 * This software is provided "AS IS," without a warranty of any 
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND 
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY 
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY 
 * DAMAGES OR LIABILITIES  SUFFERED BY LICENSEE AS A RESULT OF  OR 
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THE SOFTWARE OR 
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE 
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, 
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER 
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF 
 * THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 * 
 * You acknowledge that Software is not designed, licensed or 
 * intended for use in the design, construction, operation or 
 * maintenance of any nuclear facility. 
 */
public class TimeServer {
	private static Selector sel;

	// We can't use the normal daytime port (unless we're running as root,
	// which is unlikely), so we use this one instead
	private static int PORT = 8013;

	// The port we'll actually use
	private static int port = PORT;

	// Charset and encoder for US-ASCII
	private static Charset charset = Charset.forName("UTF-8");
	private static CharsetEncoder encoder = charset.newEncoder();

	// Direct byte buffer for writing
	private static ByteBuffer dbuf = ByteBuffer.allocateDirect(1024);

	// Open and bind the server-socket channel
	//
	private static ServerSocketChannel setup() throws IOException {
		sel = Selector.open();
		ServerSocketChannel ssc = ServerSocketChannel.open();
		InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), port);
		ssc.socket().bind(isa);
		ssc.configureBlocking(false);
		ssc.register(sel, SelectionKey.OP_ACCEPT);
		return ssc;
	}

	// Service the next request to come in on the given channel
	//
	private static void serve(ServerSocketChannel ssc) throws IOException {
		int readyChannels = sel.select();
		if (readyChannels == 0)
			return;
		// SocketChannel sc = ssc.accept();
		Iterator<SelectionKey> iter = sel.selectedKeys().iterator();
		while (iter.hasNext()) {
			SelectionKey key = iter.next();
			if (key.isAcceptable()) {
				System.out.println("acceptable");
				regesterToWriteOp(key);
			} else if (key.isConnectable()) {
				regesterToWriteOp(key);
			} else if (key.isReadable()) {
				System.out.println("readable");
			} else if (key.isWritable()) {
				System.out.println("writable");
				SocketChannel sc = null;
				try {
					sc = (SocketChannel) key.channel();
					String now = new Date().toString();
					sc.write(encoder.encode(CharBuffer.wrap(now + "\r\n")));
					System.out.println(sc.socket().getInetAddress() + " : " + now);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (sc != null) {
						sc.close();
					}
				}
			}
			iter.remove();
		}
		// try {
		//
		// sc.close();
		// } finally {
		// // Make sure we close the channel (and hence the socket)
		// sc.close();
		// }
	}

	private static void regesterToWriteOp(SelectionKey key) throws IOException, ClosedChannelException {
		ServerSocketChannel server = (ServerSocketChannel) key.channel();
		// 获得和客户端连接的通道
		SocketChannel channel = server.accept();
		// 设置成非阻塞
		channel.configureBlocking(false);
		channel.register(sel, SelectionKey.OP_WRITE);
	}

	public static void main(String[] args) throws IOException {
		if (args.length > 1) {
			System.err.println("Usage: java TimeServer [port]");
			return;
		}

		// If the first argument is a string of digits then we take that
		// to be the port number
		if ((args.length == 1) && Pattern.matches("[0-9]+", args[0]))
			port = Integer.parseInt(args[0]);

		ServerSocketChannel ssc = setup();
		for (;;)
			serve(ssc);

	}

}