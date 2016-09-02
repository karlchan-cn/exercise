package cn.com.karl.exercise.java.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.io.IOUtils;

public class FileChannelTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RandomAccessFile raf = null;
		FileChannel ch = null;
		ByteBuffer bb = ByteBuffer.allocate(2048);
		try {
			raf = new RandomAccessFile(new File("/Users/karl/Desktop/readme.txt"), "rw");
			ch = raf.getChannel();
			while (ch.read(bb) > -1) {
				bb.flip();
				if (bb.hasArray())
					System.out.println("has array:"+new String(bb.array(), "UTF-8"));
				else {
					byte[] result = new byte[2048];
					bb.get(result);
					System.out.println("else :"+new String(result, "UTF-8"));
				}
				bb.clear();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ch != null) {
				try {
					ch.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			IOUtils.closeQuietly(raf);

		}

	}

}
