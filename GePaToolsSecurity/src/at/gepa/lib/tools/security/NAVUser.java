package at.gepa.lib.tools.security;



public class NAVUser
extends BasicUser
{
	public static final String PREFIX = "WI-";
	public static final String PASSWORD_HASH = "-250826035";
	
	private boolean employee;
	private int SiSNo;
	protected String no;
	
	public NAVUser( String name, String no )
	{
		this(name, no, "CheckMyTimes");
	}
	public NAVUser( String name, String no, String key )
	{
		super(name, null, key);
		this.no = no;
		tryGetPassword(true);
		setSiSNo( 0 );
	}
	
	@Override
	protected String getHashCode() {
		return PASSWORD_HASH;
	}

	public String toString()
	{
		return name + (no.isEmpty() ? "" : " ("+cleanKeyAbbr()+")");
	}
	public String cleanKeyAbbr() {
		return no.replace(PREFIX, "");
	}
	public String getKey() {
		return no;
	}
	@Override
	public boolean isSelf() {
		return no.equalsIgnoreCase(NAVUser.currentUserKey());
	}

	public static String currentUserKey()
	{
		String un = BasicUser.currentUserKey();
		return PREFIX + un;
	}
	protected String getPwdKey() {
		return "password" + getKey();
	}
	
	@Override
	public String getPassword() 
	{
		super.getPassword();
		if( password == null && tryGetPassword() )
		{
			String pwd = super.createPasswordFromFactory();
			if( pwd == null )
			{
				tryGetPassword(password == null);
				setPassword(null);
			}
			else
			{
				if( this.password != null && !this.password.equals(pwd) )
					setPassword( pwd );
			}
		}
		return password;
	}

	private boolean tryPassword;
	private String email;
	private boolean tryGetPassword() {
		return tryPassword;
	}
	public void tryGetPassword(boolean b) {
		tryPassword = b;
	}

	public void setEmployee(boolean b) {
		employee = b;
	}
	public boolean isEmployee() {
		return employee;
	}
	public void setSiSNo(int no) {
		SiSNo = no;
	}
	public int getSiSNo() {
		return SiSNo;
	}
	public boolean hasSiSNo() {
		return SiSNo > 0;
	}
	public String getEMail() {
		return email;
	}
	public void setEMail(String string) {
		if( string != null )
		{
			string = string.replace("mail: ", "").trim();
		}
		this.email = string;
	}
	public boolean hasEMail()
	{
		return email != null && email.contains("@");
	}

}
