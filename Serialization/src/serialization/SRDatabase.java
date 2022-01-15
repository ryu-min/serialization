package serialization;

import static serialization.Serializator.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SRDatabase {
	
	private final String  HEADER = new String("DATABASE 1.0");
	private final byte CONTAINER_TYPE = ContainerType.DATABASE; 
	private String name;
	private int size = 4 + 1 + 4;
	private List<SRObject> objects = new ArrayList<SRObject>();
	
	public SRDatabase(String name) {
		this.name = name;
		this.size += name.length() + 4;
	}
	
	public String getName() {
		return name;
	}
	
	public void addObject(SRObject object) {
		objects.add(object);
		size += object.size();
	}
	
	public int size() {
		return size;	
	}
	
	public void serializeToBytes(byte[] dest, AtomicInteger pointer) {

		writeInt(dest, pointer, size);
		writeByte(dest, pointer, CONTAINER_TYPE);
		writeString(dest, pointer, name);
		
		writeInt(dest, pointer, objects.size());
		for (SRObject object : objects) {
			object.serializeToBytes(dest, pointer);
		}
	}
	
	public int serializeToBytes(byte[] dest, int pointer) {
		AtomicInteger i = new AtomicInteger(pointer);
		serializeToBytes(dest, i);
		return i.get();
	}
	public static SRDatabase deserialize(byte[] src, AtomicInteger pointer) {
		int size = readInt(src, pointer);
		byte type = readByte(src, pointer);
		String name = readString(src, pointer);
		int objectCount = readInt(src, pointer);
		
		SRDatabase db = new SRDatabase(name);
		for (int i = 0; i < objectCount; i++) {
			db.addObject(SRObject.deserialize(src, pointer));
		}
		
		return db;
	}
	
	public static SRDatabase deserialize(byte[] src, int pointer) {
		return deserialize(src, new AtomicInteger(pointer));
	}
	
	public static SRDatabase deserializeFromFile(BufferedInputStream stream) throws IOException {
		
		byte[] buffer = new byte[stream.available()];
		stream.read(buffer);
		return deserialize(buffer, 0);
	}
	
}
