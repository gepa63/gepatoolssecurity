package at.gepa.lib.tools.win;

import java.util.ArrayList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class WinRegistry {
	private String appName;
	private static WinRegistry instance;
	
	public static class KeyValue
	{
		private String key;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		private String value;
		public KeyValue( String k, String v)
		{
			key = k;
			value = v;
		}
		
	}
	
	public Preferences getAppRoot()
	{
		Preferences root = (java.util.prefs.Preferences)Preferences.userRoot();
		
		Preferences p = root.node(appName);
		return p;
	}
	
	public static WinRegistry getInstance(String key)
	{
		if( instance == null )
			instance = new WinRegistry(key);
		return instance;
	}

	public WinRegistry(String appName)
	{
		this.appName = appName;
	}
	public String getStringValue( String key, String def)
	{
		String ret = null;
		Preferences p = getAppRoot();
		ret = p.get(key, def);
		return ret;
	}

	public void setStringValue(String key, String value) {
		Preferences p = getAppRoot();
		
		if( value == null )
			p.remove(key);
		else
			p.put(key, value);
		sync();
	}

	private void sync() {
		try {
			Preferences root = (java.util.prefs.Preferences)Preferences.userRoot();
			root.flush();
			root.sync();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}

	public void removeAllFrom(String fromUser) {
		Preferences p = getAppRoot();
		try {
			String[] keyList = p.keys();
			for( String key : keyList )
			{
				if( key.startsWith(fromUser) )
				{
					p.remove(key);
				}
			}
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}

	public String getStringValue(String folder, String key, String defValue) 
	{
		Preferences p = getAppRoot().node(folder);
		return p.get(key, defValue);
	}
	public void setStringValue(String folder, String key, String value) 
	{
		Preferences p = getAppRoot().node(folder);
		p.put(key, value);
	}

	public ArrayList<KeyValue> queryFolder(String folder) {
		ArrayList<KeyValue> list = new ArrayList<KeyValue>();
		Preferences p = getAppRoot().node(folder);
		try {
			for( String k : p.keys() )
			{
				KeyValue kv = new KeyValue(k, p.get(k, "") );
				list.add(kv);
			}
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		return list;
	}
	public void clearFolder(String folder) {
		Preferences p = getAppRoot().node(folder);
		try {
			p.clear();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		return;
	}

	public boolean getBooleanValue(String folder, String key, boolean flag) 
	{
		Preferences p = getAppRoot().node(folder);
		flag = p.getBoolean(key, flag);
		return flag;
	}
	public void setBooleanValue(String folder, String key, boolean flag) 
	{
		Preferences p = getAppRoot().node(folder);
		p.putBoolean(key, flag);
	}

	public int getIntValue(String folder, String key, int value) 
	{
		Preferences p = getAppRoot().node(folder);
		return p.getInt(key, value);
	}
	public void setIntValue(String folder, String key, int value) 
	{
		Preferences p = getAppRoot().node(folder);
		p.putInt(key, value);
	}

	public float getFloatValue(String folder, String key, float value) 
	{
		Preferences p = getAppRoot().node(folder);
		return p.getFloat(key, value);
	}
	public void setFloatValue(String folder, String key, float value) 
	{
		Preferences p = getAppRoot().node(folder);
		p.putFloat(key, value);
	}

	public byte[] getByteValue(String folder, String key, byte [] value) 
	{
		Preferences p = getAppRoot().node(folder);
		return p.getByteArray(key, value);
	}
	public void setByteValue(String folder, String key, byte [] value) 
	{
		Preferences p = getAppRoot().node(folder);
		p.putByteArray(key, value);
	}
	
}
