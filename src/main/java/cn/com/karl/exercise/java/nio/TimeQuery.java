package cn.com.karl.exercise.java.nio;
/*
 * @(#)TimeQuery.java	1.2 01/12/13
 * Ask a list of hosts what time it is.  Demonstrates NIO socket channels
 * (connection and reading), buffer handling, charsets, and regular
 * expressions.
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

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.*;
import java.util.regex.*;

public class TimeQuery {

	private Selector selector;

	private SocketChannel sc;

	// The standard daytime port
	private static int DAYTIME_PORT = 8013;

	// The port we'll actually use
	private int port = DAYTIME_PORT;

	private String host;

	// Charset and decoder for US-ASCII
	private static Charset charset = Charset.forName("UTF-8");
	private static CharsetDecoder decoder = charset.newDecoder();

	// Direct byte buffer for reading
	private static ByteBuffer dbuf = ByteBuffer.allocateDirect(1024);

	public TimeQuery(String host) {
		try {
			this.selector = Selector.open();
			sc = SocketChannel.open();
			sc.configureBlocking(false);
			this.host = host;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	private void doConnect() {
		try {
			if (sc.connect(new InetSocketAddress(host, port))) {
				sc.register(selector, SelectionKey.OP_READ);
			} else
				sc.register(selector, SelectionKey.OP_CONNECT);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void doWrite(SocketChannel sc) {
		try {
			byte[] req = "QUERY TIME ORDER".getBytes();
			ByteBuffer bb = ByteBuffer.allocate(req.length);
			bb.put(req);
			bb.flip();
			sc.write(bb);
			if(!bb.hasRemaining())
				System.out.println("send mesage to server");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Ask the given host what time it is
	//
	private static void query(String host) throws IOException {
		InetSocketAddress isa = new InetSocketAddress(InetAddress.getByName(host), DAYTIME_PORT);
		SocketChannel sc = null;
		try {

			// Connect
			sc = SocketChannel.open();
			sc.connect(isa);

			// Read the time from the remote host. For simplicity we assume
			// that the time comes back to us in a single packet, so that we
			// only need to read once.
			dbuf.clear();
			sc.read(dbuf);

			// Print the remote address and the received time
			dbuf.flip();
			CharBuffer cb = decoder.decode(dbuf);
			System.out.print(isa + " : " + cb);

		} finally {
			// Make sure we close the channel (and hence the socket)
			if (sc != null)
				sc.close();
		}
	}

	public static void main(String[] args) {
		
		if (args.length < 1) {
			System.err.println("Usage: java TimeQuery [port] host...");
			return;
		}
		int firstArg = 0;
		int paramPort = 0;
		// If the first argument is a string of digits then we take that
		// to be the port number
		if (Pattern.matches("[0-9]+", args[0])) {
			paramPort = Integer.parseInt(args[0]);
			firstArg = 1;
		}

		for (int i = firstArg; i < args.length; i++) {
			String host = args[i];
			TimeQuery tq = new TimeQuery(host);
			tq.port =paramPort ;
			try {
				query(host);
			} catch (IOException x) {
				System.err.println(host + ": " + x);
			}
		}
	}

}