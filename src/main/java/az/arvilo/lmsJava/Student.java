package az.arvilo.lmsJava;

import java.sql.ResultSet;

class Student extends IPerson {
	//final private Journal journal;
	
	Student (int ID, Journal JOURNAL) {
		super(ID,JOURNAL);
	}
	
	Student (String FIN, String FIRST_NAME,String LAST_NAME, Journal JOURNAL) {
		super(FIN,FIRST_NAME,LAST_NAME,JOURNAL);
	}
	
	public String table() {
		return "student";
	}
	
	Group[] Groups () {
		try {
			ResultSet rslt = journal.statement.executeQuery("select count(*) from  " + this.journal.db_name + "." + "group__student where student_id = " + this.id);
			rslt.next();
			Group[] rtr = new Group[rslt.getInt(1)];
			rslt = journal.statement.executeQuery("select group__id from  " + this.journal.db_name + "." + "group__student where student_id = " + this.id);
		    for (int i = 0;i < rtr.length; i++) {
		    	rslt.next();
		    	rtr[i] = new Group(rslt.getInt(1),this.journal);
		    }
		    return rtr;
		} catch (Exception e) {
			e.printStackTrace();
			return new Group[0];
		}
		
	}
	
	
	
}
