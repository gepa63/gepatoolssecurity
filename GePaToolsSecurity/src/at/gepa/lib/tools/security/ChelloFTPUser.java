package at.gepa.lib.tools.security;


public class ChelloFTPUser extends BasicUser {

	public ChelloFTPUser() {
		this("gerhard.payer", null );
		readFromReg();
	}
	
	public ChelloFTPUser(String name, String pwd) {
		this( name, pwd, ChelloFTPUser.class.getSimpleName() );
	}

	public ChelloFTPUser(String name, String pwd, String regKey ) {
		super(name, pwd, regKey);
	}

}
