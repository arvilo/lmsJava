package az.arvilo.lmsJava;

import java.sql.ResultSet;

class Subject {
	private int id;
	private Journal journal;
	
	Subject (int ID, Journal JOURNAL) {
		this.id = ID;
		this.journal = JOURNAL;
	}
	
	Subject (String NAME, int HOURS, int CREDIT, int GROUP,Journal JOURNAL) {
		try {
			this.journal = JOURNAL;
			this.journal.statement.execute("insert into " + this.journal.db_name + ".subject (name,hours,credit,group__id) values ('" + NAME +"'," + HOURS + "," + CREDIT + "," + GROUP + ")");  
			ResultSet rslt = this.journal.statement.executeQuery("Select max(id) from " + this.journal.db_name + ".subject");
			rslt.next();
			this.id = rslt.getInt(1);
				if (this.journal.Data("subject", this.id, "name").equals(NAME) && Integer.parseInt(this.journal.Data("subject", id, "hours")) == HOURS && Integer.parseInt(this.journal.Data("subject", id, "credit")) == CREDIT && Integer.parseInt(this.journal.Data("subject", id, "group__id")) == GROUP) {
				} else {
					System.out.println("Can't find id!!!");
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	int getId() {
		return this.id;
	}
	
	Journal getJournal() {
		return journal;
	}
	
	String Name () {
		return this.journal.Data("subject", this.id, "name");
	}
	
	int hours () {
		return Integer.parseInt(this.journal.Data("subject", this.id, "hours"));
	}
	
	int credit () {
		return Integer.parseInt(this.journal.Data("subject", this.id, "credit"));
	}
	
	Group getGroup () {
		return new Group(Integer.parseInt(this.journal.Data("subject", this.id, "group__id")),this.journal);
	}
	
	Teacher getTeacher () {
		return new Teacher(Integer.parseInt(this.journal.Data("subject", this.id, "teacher_id")),this.journal);
	}
	
	void setTeacher (Teacher tch) {
		try {
			this.journal.statement.execute("update " + this.journal.db_name + ".subject set teacher_id = " + tch.id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	String[] getPoints () {
		try {
			String query = "select count(*) from " + this.journal.db_name + ".subject_point where subject_id  = " + this.id;
			ResultSet rslt = this.journal.statement.executeQuery(query);
			int n = 0;
			while (rslt.next()) {
				n = rslt.getInt(1);
			}
			query = "select point_id from " + this.journal.db_name + ".subject_point where subject_id  = " + this.id;
			rslt = this.journal.statement.executeQuery(query);
			if (n == 0) {
				return null;
			}
			int[] point_ids = new int[n];
			String[] rtr = new String[n];
			n = 0;
			while (rslt.next()) {
				point_ids[n] = rslt.getInt(1);
				n++;
			}
			n = 0;
			rtr[rtr.length-1] = "exam25%";
			rtr[rtr.length-2] = "exam";
			
			for (int i:point_ids) {
				if (this.journal.Data("point", i, "name").equals("exam") || this.journal.Data("point", i, "name").equals("exam25%")){
					continue;
				}
				rtr[n] = this.journal.Data("point", i , "name");
				n++;
			}
			return rtr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override public boolean equals (Object o) {
		Subject ob = (Subject) o;
		if (this.journal.equals(ob.journal) && this.id == ob.id) {
			return true;
		}
		else {
			return false;
		}
	}
}
