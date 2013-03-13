package utils;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class CreateLang 
{
	public static void main (String [] args) throws Exception
	{
		HashMap <String, String> hashMap = new HashMap<String, String>();
		hashMap.put("file_MenuBar", "Archivo");
		hashMap.put("edit_MenuBar", "Editar");
		hashMap.put("help_MenuBar", "Ayuda");
		
		ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream("lang/es_ES.lang"));
		ois.writeObject(hashMap);
		ois.writeObject(null);
		ois.close();
	}
}
