package serialization;

import static serialization.Serializator.*;

import java.util.concurrent.atomic.AtomicInteger;

public class SRString {
	
	private static final byte CONTAINER_TYPE = ContainerType.STRING; 
	private String name;
	private String value;
	private int size = 1 + 4;
		
	public SRString(String name, String value) {
		this.name = name;
		this.size += name.length() +  4;
		this.value = value;
		this.size += value.length() + 4;
	}
	
	public int size() {
		return size;
	}
	
	public String get() {
		return value;
	}
	
	public int length() {
		return value.length();
	}
	
	public String getName() {
		return name;
	}

	public void serializeToBytes(byte[] dest, AtomicInteger pointer) {
		writeInt(dest, pointer, size);
		writeByte(dest, pointer, CONTAINER_TYPE);
		writeString(dest, pointer, name);		
		writeString(dest, pointer, value);
	}
	
	public int serializeToBytes(byte[] dest, int pointer) {
		AtomicInteger i = new AtomicInteger(pointer);
		this.serializeToBytes(dest, i);
		return i.get();
	}
	
	public static SRString deserialize(byte[] src, AtomicInteger pointer) {
		readInt(src, pointer);
		readByte(src, pointer);
		String name = readString(src, pointer);
		String value = readString(src, pointer);
		return new SRString(value, name);
	}
	
	public static SRString deserializeToBytes(byte[] src, int pointer) {
		return deserialize(src, new AtomicInteger(pointer));
	}
	
}
