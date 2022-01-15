package serialization;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

public class Serializator {
	
	public static final byte[] HEADER = "RC".getBytes();
	public static final short VERSION = 0x0001;
	public static final byte FLAGS = 0;
	
	//*** "write" block ***//
	
	//** "write variable" block***//
	public static int writeBoolean(byte [] dest, int pointer, boolean value) {
		dest[pointer++] = (byte)(value ? 1 : 0);
		return pointer;
	}
	
	public static void writeBoolean(byte [] dest, AtomicInteger pointer, boolean value) {
		dest[pointer.getAndIncrement()] = (byte)(value ? 1 : 0);
	}
	
	public static int writeByte(byte[] dest, int pointer, byte value) {
		dest[pointer++] = value;
		return pointer;
	}
	
	public static void writeByte(byte[] dest, AtomicInteger pointer, byte value) {
		dest[pointer.getAndIncrement()] = value;
	}
	
	public static int writeShort(byte[] dest, int pointer, short value) {
		dest[pointer++] = (byte)((value >> 8) & 0xff);
		dest[pointer++] = (byte)((value >> 0) & 0xff);
		return pointer;
	}
	
	public static void writeShort(byte[] dest, AtomicInteger pointer, short value) {
		dest[pointer.getAndIncrement()] = (byte)((value >> 8) & 0xff);
		dest[pointer.getAndIncrement()] = (byte)((value >> 0) & 0xff);
	}
	
	public static int writeChar(byte[] dest, int pointer, char value) {
		dest[pointer++] = (byte)((value >> 8) & 0xff);
		dest[pointer++] = (byte)((value >> 0) & 0xff);
		return pointer;
	}
	
	public static void writeChar(byte[] dest, AtomicInteger pointer, char value) {
		dest[pointer.getAndIncrement()] = (byte)((value >> 8) & 0xff);
		dest[pointer.getAndIncrement()] = (byte)((value >> 0) & 0xff);
	}
	
	public static int writeInt(byte[] dest, int pointer, int value) {
		dest[pointer++] = (byte)((value >> 24) & 0xff);
		dest[pointer++] = (byte)((value >> 16) & 0xff);
		dest[pointer++] = (byte)((value >> 8)  & 0xff);
		dest[pointer++] = (byte)((value >> 0)  & 0xff);
		return pointer;
	}
	
	public static void writeInt(byte[] dest, AtomicInteger pointer, int value) {
		dest[pointer.getAndIncrement()] = (byte)((value >> 24) & 0xff);
		dest[pointer.getAndIncrement()] = (byte)((value >> 16) & 0xff);
		dest[pointer.getAndIncrement()]= (byte)((value >> 8)  & 0xff);
		dest[pointer.getAndIncrement()] = (byte)((value >> 0)  & 0xff);
	}
	public static int writeLong(byte[] dest, int pointer, long value) {
		dest[pointer++] = (byte)((value >> 56) & 0xff);
		dest[pointer++] = (byte)((value >> 48) & 0xff);
		dest[pointer++] = (byte)((value >> 40) & 0xff);
		dest[pointer++] = (byte)((value >> 32) & 0xff);
		dest[pointer++] = (byte)((value >> 24) & 0xff);
		dest[pointer++] = (byte)((value >> 16) & 0xff);
		dest[pointer++] = (byte)((value >> 8)  & 0xff);
		dest[pointer++] = (byte)((value >> 0)  & 0xff);
		return pointer;
	}
	
	public static void writeLong(byte[] dest, AtomicInteger pointer, long value) {
		dest[pointer.getAndIncrement()] = (byte)((value >> 56) & 0xff);
		dest[pointer.getAndIncrement()] = (byte)((value >> 48) & 0xff);
		dest[pointer.getAndIncrement()] = (byte)((value >> 40) & 0xff);
		dest[pointer.getAndIncrement()] = (byte)((value >> 32) & 0xff);
		dest[pointer.getAndIncrement()] = (byte)((value >> 24) & 0xff);
		dest[pointer.getAndIncrement()] = (byte)((value >> 16) & 0xff);
		dest[pointer.getAndIncrement()] = (byte)((value >> 8)  & 0xff);
		dest[pointer.getAndIncrement()] = (byte)((value >> 0)  & 0xff);
	}
	
	public static int writeFloat(byte[] dest, int pointer, float value) {
		return writeInt(dest, pointer, Float.floatToIntBits(value));
	}
	
	public static void writeFloat(byte[] dest, AtomicInteger pointer, float value) {
		writeInt(dest, pointer, Float.floatToIntBits(value));
	}
	
	public static int writeDouble(byte[] dest, int pointer, double value) {
		return  writeLong(dest, pointer, Double.doubleToLongBits(value));
	}
	
	public static void writeDouble(byte[] dest, AtomicInteger pointer, double value) {
		writeLong(dest, pointer, Double.doubleToLongBits(value));
	}
	
	public static int writeString(byte[] dest, int pointer, String string) {	
		pointer = writeInt(dest, pointer, string.length());
		pointer = writeByteArray(dest, pointer, string.getBytes());
		return pointer;
	}
	
	public static void writeString(byte[] dest, AtomicInteger pointer, String string) {	
		writeInt(dest, pointer, string.length());
		writeByteArray(dest, pointer, string.getBytes());
	}
	
	// *** "write array" block ***//
	public static int writeBooleanArray(byte[] dest, int pointer, boolean[] data) {
		for (int i = 0; i < data.length; i++) {
			pointer = writeBoolean(dest, pointer, data[i]);
		}
		return pointer;
	}
	
	public static void writeBooleanArray(byte[] dest, AtomicInteger pointer, boolean[] data) {
		for (int i = 0; i < data.length; i++) {
			writeBoolean(dest, pointer, data[i]);
		}
	}
	
	public static int writeByteArray(byte[] dest, int pointer, byte[] data) {
		for (int i = 0; i < data.length; i++) {
			dest[pointer++] =  data[i];
		}
		return pointer;
	}
	
	public static void writeByteArray(byte[] dest, AtomicInteger pointer, byte[] data) {
		for (int i = 0; i < data.length; i++) {
			dest[pointer.getAndIncrement()] =  data[i];
		}
	}
	
	public static int writeCharArray(byte[] dest, int pointer, char[] data) {
		for (int i = 0; i < data.length; i++) {
			pointer = writeChar(dest, pointer, data[i]);
		}
		return pointer;
	}
	
	public static void writeCharArray(byte[] dest, AtomicInteger pointer, char[] data) {
		for (int i = 0; i < data.length; i++) {
			writeChar(dest, pointer, data[i]);
		}
	}
	
	public static int writeShortArray(byte[] dest, int pointer, short[] data) {
		for (int i = 0; i < data.length; i++) {
			pointer = writeShort(dest, pointer, data[i]);
		}
		return pointer;
	}
	
	public static void writeShortArray(byte[] dest, AtomicInteger pointer, short[] data) {
		for (int i = 0; i < data.length; i++) {
			writeShort(dest, pointer, data[i]);
		}
	}
	
	public static int writeIntArray(byte[] dest, int pointer, int[] data) {
		for (int i = 0; i < data.length; i++) {
			pointer = writeInt(dest, pointer, data[i]);
		}
		return pointer;
	}
	
	public static void writeIntArray(byte[] dest, AtomicInteger pointer, int[] data) {
		for (int i = 0; i < data.length; i++) {
			writeInt(dest, pointer, data[i]);
		}
	}
	
	public static int writeLongArray(byte[] dest, int pointer, long[] data) {
		for (int i = 0; i < data.length; i++) {
			pointer = writeLong(dest, pointer, data[i]);
		}
		return pointer;
	}
	
	public static void writeLongArray(byte[] dest, AtomicInteger pointer, long[] data) {
		for (int i = 0; i < data.length; i++) {
			writeLong(dest, pointer, data[i]);
		}
	}
	
	public static int writeFloatArray(byte[] dest, int pointer, float[] data) {
		for (int i = 0; i < data.length; i++) {
			pointer = writeFloat(dest, pointer, data[i]);
		}
		return pointer;
	}
	
	public static void writeFloatArray(byte[] dest, AtomicInteger pointer, float[] data) {
		for (int i = 0; i < data.length; i++) {
			writeFloat(dest, pointer, data[i]);
		}
	}
	
	public static int writeDobuleArray(byte[] dest, int pointer, double[] data) {
		for (int i = 0; i < data.length; i++) {
			pointer = writeDouble(dest, pointer, data[i]);
		}
		return pointer;
	}
	
	public static void writeDoubleArray(byte[] dest, AtomicInteger pointer, double[] data) {
		for (int i = 0; i < data.length; i++) {
			writeDouble(dest, pointer, data[i]);
		}
	}
	
	//*** "read" block ***//
	
	//*** "read variable" block***///
	
	public static boolean readBoolean(byte[] data, int pointer) {
		return data[pointer] != 0;
	}
	
	public static boolean readBoolean(byte[] data, AtomicInteger pointer) {
		boolean result = data[pointer.get()] != 0;
		pointer.set(pointer.get() + 1);
		return result;	
	}
	
	public static byte readByte(byte[] data, int pointer) {
		return ByteBuffer.wrap(data).get(pointer);
	}
	public static byte readByte(byte[] data, AtomicInteger pointer) {
		byte result = ByteBuffer.wrap(data).get(pointer.get());
		pointer.set(pointer.get() + 1);
		return result;
	}
	
	public static short readShort(byte[] data, int pointer) {
		return ByteBuffer.wrap(data, pointer, 2).getShort();		
	}
	
	public static short readShort(byte[] data, AtomicInteger pointer) {
		short result = ByteBuffer.wrap(data, pointer.get(), 2).getShort();		
		pointer.set(pointer.get() + 2);
		return result;
	}
	
	public static char readChar(byte[] data, int pointer) {
		return ByteBuffer.wrap(data, pointer, 2).getChar();		
	}
	
	public static char readChar(byte[] data, AtomicInteger pointer) {
		char result = ByteBuffer.wrap(data, pointer.get(), 2).getChar();		
		pointer.set(pointer.get() + 2);
		return result;
	}
	
	public static int readInt(byte[] data, int pointer) {
		return ByteBuffer.wrap(data, pointer, 4).getInt();		
	}
	
	public static int readInt(byte[] data, AtomicInteger pointer) {
		int result = ByteBuffer.wrap(data, pointer.get(), 4).getInt();		
		pointer.set(pointer.get() + 4);
		return result;
	}
	
	public static long readLong(byte[] data, int pointer) {
		return ByteBuffer.wrap(data, pointer, 8).getLong();		
	}
	
	public static long readLong(byte[] data, AtomicInteger pointer) {
		long result = ByteBuffer.wrap(data, pointer.get(), 8).getLong();		
		pointer.set(pointer.get() + 8);
		return result;
	}
	
	public static float readFloat(byte[] data, int pointer){
		return Float.intBitsToFloat(readInt(data, pointer));
	}
	
	public static float readFloat(byte[] data, AtomicInteger pointer){
		float result = Float.intBitsToFloat(readInt(data, pointer));
		return result;
	}
	
	public static double readDouble(byte[] data, int pointer){
		return Double.longBitsToDouble(readLong(data, pointer));
	}
	
	public static double readDouble(byte[] data, AtomicInteger pointer){
		double result = Double.longBitsToDouble(readLong(data, pointer));
		return result;
	}
	
	public static String readString(byte[] data, int pointer) {
		return readString(data, new AtomicInteger(pointer));
	}
	
	public static String readString(byte[] data, AtomicInteger pointer) {
		int length = readInt(data, pointer);
		byte[] bytes = readByteArray(data, pointer, length);
		return new String(bytes, 0, length);
	}
	
	//*** "read array" block ***//
	public static boolean[] readBooleanArray(byte[] data, AtomicInteger pointer, int length) {
		boolean[] result = new boolean[length];
		assert(length <= data.length - pointer.get());
		for (int i = 0; i < length; i++) {
			result[i] = readBoolean(data, pointer);
		}
		return result;
	}
	
	public static boolean[] readBooleanArray(byte[] data, int pointer, int length) {
		return readBooleanArray(data, new AtomicInteger(pointer), length);
	}
	
	public static byte[] readByteArray(byte[] data, AtomicInteger pointer, int length) {
		byte[] result = new byte[length];
		assert(length <= data.length - pointer.get());
		for (int i = 0; i < result.length; i++) {
			result[i] = data[i + pointer.get()];
		}
		pointer.set(pointer.get() + length);
		return result;
	}
	
	public static byte[] readByteArray(byte[] data, int pointer, int length) {
		return readByteArray(data, new AtomicInteger(pointer), length);
	}
	
	
	
	public static short[] readShortArray(byte[] data, AtomicInteger pointer, int length) {
		short[] result = new short[length];
		for (int i = 0; i < length; i++) {
			result[i] = readShort(data, pointer);
		}
		return result;
	}
	
	public static short[] readShortArray(byte[] data, int pointer, int length) {
		return readShortArray(data, new AtomicInteger(pointer), length);
	}
	
	
	public static char[] readCharArray(byte[] data, AtomicInteger pointer, int length) {
		char[] result = new char[length];
		for (int i = 0; i < length; i++) {
			result[i] = readChar(data, pointer);
		}
		return result;
	}
	
	public static char[] readCharArray(byte[] data, int pointer, int length) {
		return readCharArray(data, new AtomicInteger(pointer), length);
	}
	
	public static int[] readIntArray(byte[] data, AtomicInteger pointer, int length) {
		int[] result = new int[length];
		for (int i = 0; i < length; i++) {
			result[i] = readInt(data, pointer);
		}
		return result;
	}
	
	public static int[] readIntArray(byte[] data, int pointer, int length) {
		return readIntArray(data, new AtomicInteger(pointer), length);
	}
	
	
	public static long[] readLongArray(byte[] data, AtomicInteger pointer, int length) {
		long[] result = new long[length];
		for (int i = 0; i < length; i++) {
			result[i] = readLong(data, pointer);
		}
		return result;
	}
	
	public static long[] readLongArray(byte[] data, int pointer, int length) {
		return readLongArray(data, new AtomicInteger(pointer), length);
	}
	
	
	public static float[] readFloatArray(byte[] data, AtomicInteger pointer, int length) {
		float[] result = new float[length];
		for (int i = 0; i < length; i++) {
			result[i] = readFloat(data, pointer);
		}
		return result;
	}
	
	public static float[] readFloatArray(byte[] data, int pointer, int length) {
		return readFloatArray(data, new AtomicInteger(pointer), length);
	}
	
	public static double[] readDoubleArray(byte[] data, AtomicInteger pointer, int length) {
		double[] result = new double[length];
		for (int i = 0; i < length; i++) {
			result[i] = readDouble(data, pointer);
		}
		return result;
	}
	
	public static double[] readDobuleArray(byte[] data, int pointer, int length) {
		return readDoubleArray(data, new AtomicInteger(pointer), length);
	}
	
	
}
