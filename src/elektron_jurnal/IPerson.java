package elektron_jurnal;

import java.sql.ResultSet;

abstract class IPerson {
	
	protected Journal journal;
	protected int id;
	
	//static protected final String table = this.toString().substring(this.toString().indexOf(".")+1,this.toString().indexOf("@")).toLowerCase();
	
	abstract protected String table();
	
	IPerson (int ID, Journal JOURNAL) {
		this.id = ID;
		this.journal = JOURNAL;
	}
	
	IPerson (String FIN, String FIRST_NAME,String LAST_NAME,Journal JOURNAL) {
		this.journal = JOURNAL;
		try {
			this.journal.statement.execute("insert into " + this.journal.db_name + "." + this.table() + "(fin,first_name,last_name) values ('" + FIN + "','" + FIRST_NAME + "','" + LAST_NAME + "')");
			ResultSet rslt = this.journal.statement.executeQuery("Select max(id) from " + this.journal.db_name + "." + this.table());
			rslt.next();
			this.id = rslt.getInt(1);
			if (this.journal.Data(this.table(), id, "fin").equals(FIN) && this.journal.Data(this.table(), id, "first_name").equals(FIRST_NAME) && this.journal.Data(this.table(), id, "last_name").equals(LAST_NAME)) {
			} else {
				System.out.println("Can't find id!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public int getId() {
		return this.id;
	}

	public String FirstName () {
		return journal.Data(this.table(), this.id, "first_name");
	}
	
	public String LastName () {
		return journal.Data(this.table(), this.id, "last_name");
	}
	
	public String Fin () {
		return journal.Data(this.table(), this.id, "fin");
	}
	
	public String Birth () {
		return journal.Data(this.table(), this.id, "birth");
	}
	
	public void Update (String column,String value) {
		try {
			this.journal.statement.execute("update " +  this.journal.db_name + "." + this.table() + " set " + column + " = '" + value + "' where id = " + this.id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Update (String column,int value) {
		try {
			this.journal.statement.execute("update " +  this.journal.db_name + "." + this.table() + " set " + column + " = " + value + " where id = " + this.id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
}
