package at.gepa.lib.tools.security;

import java.util.UUID;

public class SecurityKey 
{
	private static final int LEN = 3;
	private int baseMask;
	private String securityKey;
	
	public SecurityKey(String key) 
	{
		this(key, null);
	}
	public SecurityKey(String key, String seckey) 
	{
		baseMask = Integer.parseInt( key.substring(key.length()-(LEN), key.length()) );
		this.securityKey = seckey;
	}

	public String getSecurityKey() {
		return securityKey;
	}
	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}
	public void verify() throws Exception
	{
		UUID uuid = UUID.fromString(securityKey);
		long lsb = uuid.getLeastSignificantBits();
		if( !((lsb & baseMask) == baseMask) )
			throw new Exception("The Key is not valid!");
	}
	public String createSecKey()
	{
		long lsb = UUID.randomUUID().getLeastSignificantBits();
		lsb |= baseMask;
		setSecurityKey( new UUID(UUID.randomUUID().getMostSignificantBits(), lsb).toString() );
		return getSecurityKey();
	}
	public boolean hasSecKey() {
		return securityKey != null && !securityKey.isEmpty();
	}
}
