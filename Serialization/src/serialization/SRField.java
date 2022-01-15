package serialization;

import static serialization.Serializator.*;

import java.util.concurrent.atomic.AtomicInteger;


public class SRField {
	
	private static final byte CONTAINER_TYPE = ContainerType.FIELD; 
	private String name;
	
	private byte type;
	private byte[] data;
	private int size =  4 + 1 + 4 + 1;
	
	private SRField(String name, byte type, byte[] data)
	{
		this.name = name;
		size += name.length();
		this.type = type;
		this.data = new byte[Type.getSize(type)];
		this.size += Type.getSize(type);
		writeByteArray(this.data, 0 , data);
	}
	
	public SRField(String name, boolean value) {
		
		this.name = name;
		size += name.length();
		this.type = Type.BOOLEAN;
		this.data = new byte[Type.getSize(type)];
		this.size += Type.getSize(type);
		writeBoolean(this.data, 0, value);
		
	}
	
	public SRField(String name, byte value) {
		
		this.name = name;
		this.type = Type.BYTE;
		this.data = new byte[Type.getSize(type)];
		this.size += Type.getSize(type);
		writeByte(this.data, 0, value);
		
	}
	public SRField(String name, short value) {
		
		this.name = name;
		size += name.length();
		this.type = Type.SHORT;
		this.data = new byte[type];
		this.size += Type.getSize(type);
		writeShort(this.data, 0, value);
		
	}
	public SRField(String name, char value) {
		
		this.name = name;
		size += name.length();
		this.type = Type.CHAR;
		this.data = new byte[Type.getSize(type)];
		this.size += Type.getSize(type);
		writeChar(this.data, 0, value);
		
	}
	public SRField(String name, int value) {
		
		this.name = name;
		size += name.length();
		this.type = Type.INT;
		this.data = new byte[Type.getSize(type)];
		this.size += Type.getSize(type);
		writeInt(this.data, 0, value);
		
	}
	public SRField(String name, long value) {
		
		this.name = name;
		size += name.length();
		this.type = Type.LONG;
		this.data = new byte[Type.getSize(type)];
		this.size += Type.getSize(type);
		writeLong(this.data, 0, value);
		
	}
	public SRField(String name, float value) {
		
		this.name = name;
		size += name.length();
		this.type = Type.FLOAT;
		this.data = new byte[Type.getSize(type)];
		this.size += Type.getSize(type);
		writeFloat(this.data, 0, value);
		
	}
	
	public SRField(String name, double value) {
		
		this.name = name;
		size += name.length();
		this.type = Type.DOUBLE;
		this.data = new byte[Type.getSize(type)];
		this.size += Type.getSize(type);
		writeDouble(this.data, 0, value);
		
	}
	
	
	public boolean getBoolean() {
		if (Byte.compare(type, Type.BOOLEAN) != 0) {
			throw new ClassCastException("Type is not 'Boolean'");
		}
		
		return readBoolean(data, 0);
	}
	
	public byte getByte() {
		if (Byte.compare(type, Type.BYTE) != 0) {
			throw new ClassCastException("Type is not 'Boolean'");
		}
		
		return readByte(data, 0);
	}
	
	public short getShort() {
		if (Byte.compare(type, Type.SHORT) != 0) {
			throw new ClassCastException("Type is not 'Boolean'");
		}
		
		return readShort(data, 0);
	}
	
	public char getChar() {
		if (Byte.compare(type, Type.CHAR) != 0) {
			throw new ClassCastException("Type is not 'Boolean'");
		}
		
		return readChar(data, 0);
	}
	
	public int getInt() {
		if (Byte.compare(type, Type.INT) != 0) {
			throw new ClassCastException("Type is not 'Boolean'");
		}
		
		return readInt(data, 0);
	}
	
	public long getLong() {
		if (Byte.compare(type, Type.LONG) != 0) {
			throw new ClassCastException("Type is not 'Boolean'");
		}
		
		return readLong(data, 0);
	}
	
	public float getFloat() {
		if (Byte.compare(type, Type.FLOAT) != 0) {
			throw new ClassCastException("Type is not 'Boolean'");
		}
		
		return readFloat(data, 0);
	}
	
	public double getDouble() {
		if (Byte.compare(type, Type.DOUBLE) != 0) {
			throw new ClassCastException("Type is not 'Boolean'");
		}
		
		return readDouble(data, 0);
	}
	
	
	public int getSize() {
		return size;
	}
	
	public String getName() {
		return name;
	}
	
	public void serializeToBytes(byte[] dest, AtomicInteger pointer) {
		writeInt(dest, pointer, size);
		writeByte(dest, pointer, CONTAINER_TYPE);
		writeString(dest, pointer, name);
		writeByte(dest, pointer, type);
		writeByteArray(dest, pointer, data);
	}
	
	public int serializeToBytes(byte[] dest, int pointer) {
		AtomicInteger i = new AtomicInteger(pointer);
		this.serializeToBytes(dest, i);
		return i.get();
	}
	
	
	public static SRField Deserialize(byte[] data, AtomicInteger pointer) {
		readInt(data, pointer);		
		readByte(data, pointer);		
		String name = readString(data, pointer);
		byte type = readByte(data, pointer);
		byte[] data_ = readByteArray(data, pointer, Type.getSize(type));
		
		return new SRField(name, type, data_);
	}
	
	public static SRField Deserialize(byte[] data, int pointer) {
		return Deserialize(data, new AtomicInteger(pointer));
	}
	
	
	
}






