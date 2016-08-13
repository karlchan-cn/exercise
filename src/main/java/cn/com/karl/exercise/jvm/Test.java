package cn.com.karl.exercise.jvm;
import java.lang.reflect.Field;
import java.util.Arrays;
import sun.misc.Unsafe;

public class Test {
	private static int byteArrayBaseOffset;

	@SuppressWarnings("restriction")
	public static void main(String[] args) throws SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {
		Unsafe unSafe = Unsafe.getUnsafe();
		Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
		theUnsafe.setAccessible(true);
		Unsafe UNSAFE = (Unsafe) theUnsafe.get(null);
		System.out.println(UNSAFE);

		byte[] data = new byte[10];
		System.out.println(Arrays.toString(data));
		byteArrayBaseOffset = UNSAFE.arrayBaseOffset(byte[].class);
		UNSAFE.getObjectVolatile(null, 1);
		System.out.println(byteArrayBaseOffset);
		UNSAFE.putByte(data, byteArrayBaseOffset, (byte) 1);
		UNSAFE.putByte(data, byteArrayBaseOffset + 5, (byte) 5);
		System.out.println(Arrays.toString(data));
	}
}
