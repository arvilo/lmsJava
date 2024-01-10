package elektron_jurnal;

import java.sql.ResultSet;

class Teacher extends IPerson{
	//final private Journal journal;
	//final private int id;
	Teacher (int ID, Journal JOURNAL) {
		super(ID,JOURNAL);
	}
	
	Teacher (String FIN, String FIRST_NAME,String LAST_NAME, Journal JOURNAL) {
		super(FIN,FIRST_NAME,LAST_NAME,JOURNAL);
	}
	
	public String table() {
		return "teacher";
	}
	
	
	int NumberOfSubject () {
		String query = "select count(*) from " + this.journal.db_name + ".subject where teacher_id = " + this.id + " group by techer_id";
		try {
			ResultSet result = this.journal.statement.executeQuery(query);
			result.next();
			return result.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	Subject[] Subjects () {
		int n = this.NumberOfSubject();
		Subject[] rtr = new Subject[n];
		String query = "select id from " + this.journal.db_name + ".subject where teacher_id = " + this.id;
		try {
			ResultSet result;
			result = this.journal.statement.executeQuery(query);
			for (int i = 0;i < n;i++) {
				result.next();
				rtr[i] = new Subject(result.getInt(1),this.journal);
			}
			return rtr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
