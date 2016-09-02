package cn.com.karl.apache.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
		ByteBuf in = (ByteBuf) msg;
		try {
			// Discard the received data silently.
			// ((ByteBuf) msg).release(); // (3)
			if (in.isReadable())
				System.out.print(in.toString(CharsetUtil.UTF_8));
			ctx.write(msg);
			ctx.flush();
		} finally {
			ReferenceCountUtil.release(msg);
		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}
}