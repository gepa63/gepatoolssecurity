package at.gepa.lib.tools.security;

import org.jasypt.util.text.BasicTextEncryptor;

import at.gepa.lib.tools.win.WinRegistry;


public class BasicUser {
	public static final String HASHCODE = "26358185";
	
	protected String name;
	protected String password;
	protected String regKey;
	
	protected IPasswordFactory pwdFactory;
	
	public static BasicUser createInstance( String name, String pwd )
	{
		return new BasicUser( name, pwd );
	}
	public BasicUser()
	{
		this(BasicUser.currentUserKey());
	}
	public BasicUser( String name )
	{
		this(name, null );
	}
	public BasicUser( String name, String pwd )
	{
		this(name, pwd, BasicUser.class.getSimpleName() );
	}
	public BasicUser( String name, String pwd, String regKey )
	{
		this.name = name;
		password = pwd;
		this.regKey = regKey;
		setPassowordFactory(null);
		setFullName(null);
	}
	public String toString()
	{
		return name;
	}
	public boolean isSelf() {
		return name.equalsIgnoreCase(BasicUser.currentUserKey());
	}
	
	public static String currentUserKey()
	{
		String un = System.getProperty("user.name").toUpperCase();
		return un;
	}
	
	public boolean isSame(BasicUser user) {
		if( name ==null  || password == null || user.name == null || user.password == null) return false;
		return name.equals(user.name) && password.equals(user.password);
	}
	
	public String getPassword() 
	{
		if( password == null )
		{
			readFromReg();
		}
		return password;
	}
	public void readFromReg() {
		WinRegistry reg = WinRegistry.getInstance(regKey);
		String encryptedPwd = reg.getStringValue(getPwdKey(), null);
		reg = null;
		
		if( encryptedPwd == null ) return;
		
		BasicTextEncryptor te = new BasicTextEncryptor();
		te.setPassword( getHashCode() );
		//te.setPassword( SimpleSiSModel.PASSWORD_HASH );
		String pwd = te.decrypt(encryptedPwd);
		setPassword( pwd );
	}
	protected String getHashCode() {
		return BasicUser.HASHCODE;
	}
	public void saveToReg() {
		BasicTextEncryptor te = new BasicTextEncryptor();
		te.setPassword(getHashCode());//SimpleSiSModel.PASSWORD_HASH);
		String encryptedPassword = te.encrypt(password);
		WinRegistry reg = WinRegistry.getInstance(regKey /*"CheckMyTimes"*/);
		reg.setStringValue(getPwdKey(), encryptedPassword);
		reg = null;
	}
	protected String getPwdKey() {
		return "password" + getUserName();
	}
	public void setPassword(String pwd) {
		password = pwd;
	}
	public void resetPassword() {
		setPassword(null);

		clearPwdFromRegistry();
	}
	public void clearPwdFromRegistry() {
		WinRegistry reg = WinRegistry.getInstance(regKey /*"CheckMyTimes"*/);
		reg.setStringValue(getPwdKey(), null);
		reg = null;
	}
	public void validate() throws Exception {
		if( name == null || name.isEmpty() ) throw new Exception("Benutzername is leer!");
	}
	public String getUserName() {
		return name;
	}
	public void setPassowordFactory(IPasswordFactory iPasswordFactory) {
		this.pwdFactory = iPasswordFactory;
	}
	public String createPasswordFromFactory() {
		if( !hasPasswordFactory() ) return null;
		return pwdFactory.createPassword();
	}
	private boolean hasPasswordFactory() {
		return pwdFactory != null;
	}
	
	private String fullname = null;
	public String getFullUserName()
	{
		return fullname == null ? getUserName() : fullname;
	}
	public void setFullName(String string) {
		fullname = string;
	}
}
