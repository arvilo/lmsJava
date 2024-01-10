package elektron_jurnal;

import java.sql.ResultSet;

class Group {
	private Journal journal;
	private int id;
	
	Group (int ID, Journal JOURNAL) {
		this.id = ID;
		this.journal = JOURNAL;
	}
	
	Group (String NUMBER, Journal JOURNAL) {
		this.journal = JOURNAL;
		try {
			this.journal.statement.execute("insert into " + this.journal.db_name + "." + "group_ (number) values ('" + NUMBER + "')");
			ResultSet rslt = this.journal.statement.executeQuery("Select max(id) from " + this.journal.db_name + ".group_");
			rslt.next();
			this.id = rslt.getInt(1);
			if (this.journal.Data("group_", this.id, "number").equals(NUMBER)) {
			} else {
			System.out.println("Can't find id!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	int NumberOfStudent () {
		String query = "select count(*) from " + this.journal.db_name + ".group__student where group__id = " + this.id;
		try {
			ResultSet result = this.journal.statement.executeQuery(query);
			result.next();
			return result.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	Student[] Students() {
		int n = this.NumberOfStudent();
		Student[] rtr = new Student[n];
		String query = "select student_id from " + this.journal.db_name + ".group__student where group__id = " + this.id;
		try {
			ResultSet result;
			result = this.journal.statement.executeQuery(query);
			for (int i = 0;i < n;i++) {
				result.next();
				rtr[i] = new Student(result.getInt(1),this.journal);
			}
			return rtr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	int NumberOfSubject () {
		String query = "select count(*) from " + this.journal.db_name + ".subject where group__id = " + this.id;
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
		String query = "select id from " + this.journal.db_name + ".subject where group__id = " + this.id;
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
	
	String Specialty () {
		return this.journal.Data("group_", this.id, "specialty");
	}
	
	void SetSpecialty (String value) {
		try {
			this.journal.statement.execute("update " + this.journal.db_name + "." + "group_ set specialty = '" + value + "' where id = " + this.id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	String getNumber() {
		return this.journal.Data("group_", this.id, "number");
	}
}
