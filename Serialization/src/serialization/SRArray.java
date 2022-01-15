package serialization;

import static serialization.Serializator.*;

import java.util.concurrent.atomic.AtomicInteger;

public class SRArray {
	
	private static final byte CONTAINER_TYPE = ContainerType.ARRAY; 
	private String name;
	private byte type;
	private int count;  
	private byte[] data;
		
	private int size = 1 + 4 + 4 + 1 + 4;

	public SRArray (String name, boolean[] data) {	
		assert(type >= 0 && type <= 8);
		this.name = name;
		this.type = Type.BOOLEAN;
		this.count = data.length;
		writeBooleanArray(this.data, 0, data);
		this.size += Type.getSize(type) * data.length;
	}
	

	public SRArray (String name, byte[] data) {	
		assert(type >= 0 && type <= 8);
		this.name = name;
		this.size += name.length();
		this.type = Type.BYTE;
		this.data = new byte[data.length * Type.getSize(type)];
		this.count = data.length;
		writeByteArray(this.data, 0, data);
		this.size += Type.getSize(type) * data.length;
	}

	public SRArray (String name, short[] data) {	
		assert(type >= 0 && type <= 8);
		this.name = name;
		this.size += name.length();
		this.type = Type.SHORT;
		this.data = new byte[data.length * Type.getSize(type)];
		this.count = data.length;
		writeShortArray(this.data, 0, data);
		this.size += Type.getSize(type) * data.length;
	}

	public SRArray (String name, char[] data) {	
		assert(type >= 0 && type <= 8);
		this.name = name;
		this.size += name.length();
		this.type = Type.CHAR;
		this.data = new byte[data.length * Type.getSize(type)];
		writeCharArray(this.data, 0, data);
		this.size += Type.getSize(type) * data.length;
	}

	public SRArray (String name, int[] data) {	
		assert(type >= 0 && type <= 8);
		this.name = name;
		this.size += name.length();
		this.type = Type.INT;
		this.data = new byte[data.length * Type.getSize(type)];
		this.count = data.length;
		writeIntArray(this.data, 0, data);
		this.size += Type.getSize(type) * data.length;
	}

	public SRArray (String name, long[] data) {	
		assert(type >= 0 && type <= 8);
		this.name = name;
		this.size += name.length();
		this.type = Type.LONG;
		this.data = new byte[data.length * Type.getSize(type)];
		this.count = data.length;
		writeLongArray(this.data, 0, data);
		this.size += Type.getSize(type) * data.length;
	}

	public SRArray (String name, float[] data) {	
		assert(type >= 0 && type <= 8);
		this.name = name;
		this.size += name.length();
		this.type = Type.FLOAT;
		this.data = new byte[data.length * Type.getSize(type)];
		this.count = data.length;
		writeFloatArray(this.data, 0, data);
		this.size += Type.getSize(type) * data.length;
	}
	
	public SRArray (String name, double[] data) {	
		assert(type >= 0 && type <= 8);
		this.name = name;
		this.size += name.length();
		this.type = Type.DOUBLE;
		this.data = new byte[data.length * Type.getSize(type)];
		this.count = data.length;
		writeDobuleArray(this.data, 0, data);
		this.size += Type.getSize(type) * data.length;
	}
	
	public SRArray (String name, byte type, byte[] data) {
		this.name = name;
		this.size += name.length();
		this.type = type;
		this.data = data;
		this.count = data.length / Type.getSize(type);
		this.size += data.length;
	}
	
	public int size() {
		return size;
	}
	
	public boolean[] getBooleanArray() {
		if (Byte.compare(type, Type.BOOLEAN) != 0) {
			throw new ClassCastException("Type is not 'Boolean'");
		}
		
		return readBooleanArray(data, 0, count);
	}
	
	public byte[] getByteArray() {
		if (Byte.compare(type, Type.BYTE) != 0) {
			throw new ClassCastException("Type is not 'Byte'");
		}
		
		return readByteArray(data, 0, count);
	}
	
	public short[] getShortArray() {
		if (Byte.compare(type, Type.SHORT) != 0) {
			throw new ClassCastException("Type is not 'Short'");
		}
		
		return readShortArray(data, 0, count);
	}
	
	public char[] getCharArray() {
		if (Byte.compare(type, Type.CHAR) != 0) {
			throw new ClassCastException("Type is not 'Char'");
		}
		
		return readCharArray(data, 0, count);
	}
	
	public int[] getIntArray() {
		if (Byte.compare(type, Type.INT) != 0) {
			throw new ClassCastException("Type is not 'Int'");
		}
		
		return readIntArray(data, 0, count);
	}
	
	public long[] getLongArray() {
		if (Byte.compare(type, Type.LONG) != 0) {
			throw new ClassCastException("Type is not 'Long'");
		}
		
		return readLongArray(data, 0, count);
	}
	
	public float[] getFloatArray() {
		if (Byte.compare(type, Type.FLOAT) != 0) {
			throw new ClassCastException("Type is not 'Float'");
		}
		
		return readFloatArray(data, 0, count);
	}
	
	public double[] getDoubleArray() {
		if (Byte.compare(type, Type.DOUBLE) != 0) {
			throw new ClassCastException("Type is not 'Double'");
		}
	
		return readDobuleArray(data, 0, count);
	}
	
	
	
	public void serializeToBytes(byte[] dest, AtomicInteger pointer) {
		writeInt(dest, pointer, size);
		writeByte(dest, pointer, CONTAINER_TYPE);
		writeString(dest, pointer, name);
		writeByte(dest, pointer, type);
		writeInt(dest, pointer, count);
		writeByteArray(dest, pointer, data);
	}
	
	public int serializeToBytes(byte[] dest, int pointer) {
		AtomicInteger i = new AtomicInteger(pointer);
		this.serializeToBytes(dest, i);
		return i.get();
	}
	
	public static SRArray deserialize(byte[] src, AtomicInteger pointer) {
		readInt(src, pointer);
		readByte(src, pointer);
		int length = readInt(src, pointer);
		byte[] name = readByteArray(src, pointer, length);
		byte type = readByte(src, pointer);
		int count = readInt(src, pointer);
		
		int dataLength = Type.getSize(type) * count;
		byte[] data = readByteArray(src, pointer, dataLength);
		
		return new SRArray(new String(name, 0, length), type, data);
	}
	
	public static SRArray deserialize(byte[] src, int pointer) {
		return deserialize(src,new AtomicInteger(pointer));
	}
}
