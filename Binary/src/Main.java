import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.concurrent.atomic.AtomicInteger;

import serialization.*;

public class Main {
	
	static void printBytes(byte[] data) {
		for (int i = 0; i < data.length; i++) {
			System.out.printf("0x%x ", data[i]);
		}
	}
	
	static void saveToFile(String path, byte[] data) {
		try {
			
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path));
			stream.write(data);
			stream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
		
	public static void foo(AtomicInteger i) {	
		i.getAndIncrement();
	}
	public static void main(String[] args) {
		
		SRString string1 = new SRString("stringName1", "value1");
		SRString string2 = new SRString("stringName2", "value2");
		
		SRField field = new SRField("fieldName", 12L);
		
		SRArray arr1 = new SRArray("arrName1", new double[]{10.3, 10.3});
		SRArray arr2 = new SRArray("arrName2", new int[]{10, 11});
		
		SRObject obj = new SRObject("objName");
		
		obj.addString(string1);
		obj.addField(field);
		obj.addArray(arr1);
		
		SRDatabase db = new SRDatabase("name");
		db.addObject(obj);
		byte[] stream = new byte[db.size()];
		db.serializeToBytes(stream, 0);
		saveToFile("test.t", stream);
		SRDatabase temp;
		try {
			FileInputStream fileStream = new FileInputStream("test.t");
			BufferedInputStream bufferedStream = new BufferedInputStream(fileStream);
			temp = SRDatabase.deserializeFromFile(bufferedStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	
		
		
		printBytes(stream);
		
	}
}