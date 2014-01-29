package de.srsoftware.tools.translations;
import java.util.Locale;
import java.util.TreeMap;


public class Translations {
	
	private static TreeMap<String, String> trans=new TreeMap<String, String>();
	
	public static String get(String key){		
		if (trans.containsKey(key)) return trans.get(key);
		return key;
	}

	private static Translation loadTranslations(String path) {		
		String locale=Locale.getDefault().getLanguage().toUpperCase();
		System.out.print("Loading translation Translation"+locale+"...");
		Translation trans;
		try {
			trans = (Translation) Translation.class.getClassLoader().loadClass(path+"Translation"+locale).newInstance();
			System.out.println("success.");
			return trans;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Failed. Using en.");
		return new Translation();
	}
	
	public static String get(String key, Object insert) {
		String result=get(key);
		if (result==null) result=key;
		if (insert instanceof Object []){
			Object[] oarray = (Object[])insert;
			for (Object o:oarray){
				result=result.replaceFirst("#", string(o));
			}
			return result;
		}		
		return result.replace("#", string(insert));
	}

	private static String string(Object insert) {
		if (insert==null) return "null";
	  return insert.toString();
  }

	public static void getFor(@SuppressWarnings("rawtypes") Class cl) {
		trans=loadTranslations(cl.getPackage().getName()+".").getTranslations();
  }
}
