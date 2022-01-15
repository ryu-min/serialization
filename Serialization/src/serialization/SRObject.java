package serialization;

import static serialization.Serializator.readByte;
import static serialization.Serializator.readInt;
import static serialization.Serializator.readShort;
import static serialization.Serializator.readString;
import static serialization.Serializator.writeByte;
import static serialization.Serializator.writeInt;
import static serialization.Serializator.writeString;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SRObject {
	
	public static final byte CONTAINER_TYPE = ContainerType.OBJECT; 
	String name;
	
	private List<SRField> fields   = new ArrayList<SRField>();
	private List<SRArray> arrays   = new ArrayList<SRArray>();
	private List<SRString> strings = new ArrayList<SRString>();
	
	public int size = 1 + 4  + 4 + 4 + 4;
	
	public SRObject(String name) {
		this.name = name;
		size += name.length() + 4;
	}
	
	
	public String getName() {
		return this.name;
	}
	
	public void addField(SRField field) {
		size += field.getSize();
		fields.add(field);
	}

	public void addArray(SRArray array) {
		size += array.size();
		arrays.add(array);
	}
	
	public void addString(SRString string) {
		size += string.size();
		strings.add(string);
	}
	
	public int size() {
		return size;	
	}
	
	public void serializeToBytes(byte[] dest, AtomicInteger pointer) {
		writeInt(dest, pointer, size);
		writeByte(dest, pointer, CONTAINER_TYPE);
		writeString(dest, pointer, name);
		
		writeInt(dest, pointer, fields.size());
		for (SRField field : fields) {
			field.serializeToBytes(dest, pointer);
		}
		
		writeInt(dest, pointer, arrays.size());
		for (SRArray array : arrays) {
			array.serializeToBytes(dest, pointer);
		}
		
		writeInt(dest, pointer, strings.size());
		for (SRString string : strings) {
			string.serializeToBytes(dest, pointer);
		}
	}
	
	public int serializeToBytes(byte[] dest, int pointer) {
		AtomicInteger i = new AtomicInteger(pointer);
		this.serializeToBytes(dest, i);
		return i.get();
	}
	
	public static SRObject deserialize(byte[] src, AtomicInteger pointer) {
		int size = readInt(src, pointer);
		byte type = readByte(src, pointer);
		String name = readString(src, pointer);
		
		SRObject object = new SRObject(name);
		
		int fieldCount = readInt(src, pointer);
		for (int i = 0; i < fieldCount; i++) {
			object.addField(SRField.Deserialize(src, pointer));
		}
		
		int arrayCount = readInt(src, pointer);
		for (int i = 0; i < arrayCount; i++) {
			object.addArray(SRArray.deserialize(src, pointer));
		}
		
		int stringCount = readInt(src, pointer);
		for (int i = 0; i < stringCount; i++) {
			object.addString(SRString.deserialize(src, pointer));
		}
		
		return object;		
	}
	
	public static SRObject deserialize(byte[] src, int pointer) {
		return deserialize(src, new AtomicInteger(pointer));
	}
	
}
