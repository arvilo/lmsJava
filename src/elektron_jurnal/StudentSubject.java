package elektron_jurnal;

import java.sql.ResultSet;

class StudentSubject {
	Student student;
	Subject subject;
	
	StudentSubject (Student STUDENT,Subject SUBJECT,Journal JOURNAL) {
		outerloop:
		for (Group i: STUDENT.Groups()) {
			for (Subject j: i.Subjects()) {
				if (j.equals(SUBJECT)) {
					this.student = STUDENT;
					this.subject = SUBJECT;
					break outerloop;
				}
			}
		}
	}
	
	Student getStudent () {
		return this.student;
	}
	
	Subject getSubject () {
		return this.subject;
	}
	
	String[] Points() {
		try {
			ResultSet rslt = this.subject.getJournal().statement.executeQuery("select count(*) from " + this.subject.getJournal().db_name + "." + "subject_point where subject_id = " + this.subject.getId());
			rslt.next();
			String[] rtr = new String[rslt.getInt(1)];
			int[] IDS = new int[rslt.getInt(1)];
			rslt = this.subject.getJournal().statement.executeQuery("select point_id from " + this.subject.getJournal().db_name + "." + "subject_point where subject_id = " + this.subject.getId());
			for (int i = 0;i < IDS.length; i++) {
				rslt.next();
				IDS[i] = rslt.getInt(1);
			}
			 for (int i = 0; i < rtr.length;i++) {
				 rtr[i] = this.subject.getJournal().Data("point", IDS[i], "name");
			 }
			return rtr;
		} catch (Exception e) {
			e.printStackTrace();
			return new String[0];
		}
	}
	
	int[] getResultIdsByPoint (String point_name) {
		int[] rtr;
		try {
			ResultSet rslt = this.subject.getJournal().statement.executeQuery("select id from " + this.subject.getJournal().db_name + "." + "point where name = '" + point_name + "'");
			rslt.next();
			int point_id = rslt.getInt(1);
			rslt = this.subject.getJournal().statement.executeQuery("select id from " + this.subject.getJournal().db_name + ".subject_point where subject_id = " + this.subject.getId() + " and point_id = " + point_id);
			rslt.next();
			int subject_point_id = rslt.getInt(1);
			rslt = this.subject.getJournal().statement.executeQuery("select count(*) from " + this.subject.getJournal().db_name + ".result where subject_point_id = " + subject_point_id + " and student_id = " + this.student.id);
			rslt.next();
			rtr = new int[rslt.getInt(1)];
			rslt = this.subject.getJournal().statement.executeQuery("select id from " + this.subject.getJournal().db_name + ".result where subject_point_id = " + subject_point_id + " and student_id = " + this.student.id);
			for (int i = 0;i < rtr.length;i++) {
				rslt.next();
				rtr[i] = rslt.getInt(1);
			}
			return rtr;
		} catch (Exception e) {
			e.printStackTrace();
			return new int[0];
		}
	}
	
	
	String[] getResult (int ID) {
		String[] rtr = new String[6];
		// 0 - amount
	    // 1 - max
		// 2 - min
		// 3 - active
		// 4 - is_changed
		
		rtr[0] = this.subject.getJournal().Data("result", ID, "amount");
		rtr[1] = this.subject.getJournal().Data("subject_point", Integer.parseInt(this.subject.getJournal().Data("result", ID, "subject_point_id")), "max");
		rtr[2] = this.subject.getJournal().Data("subject_point", Integer.parseInt(this.subject.getJournal().Data("result", ID, "subject_point_id")), "min");
		rtr[3] = this.subject.getJournal().Data("result", ID, "active");
		rtr[4] = this.subject.getJournal().Data("result", ID, "is_changed");
		int amount = Integer.parseInt(rtr[0]);
		int min = Integer.parseInt(rtr[2]);
		if (rtr[4].equals("1")) {
			if (amount < min) {
				rtr[5] = "fail";
			}
			else {
				rtr[5] = "comp";
			}
		}
		else {
			rtr[5] = "null";
		}
		return rtr;
	}
	
	//status enum('not started','started','completed','failed','full failed')
	
	int BeginingPoint () {
		int rtr = 0;
		for (String i: this.Points()) {
			if (i.equals("exam") || i.equals("exam25%")) {
				continue;
			}
			rtr += Integer.parseInt(this.getResult((this.getResultIdsByPoint(i)[0]))[0]);
		}
		return rtr;
	}
	
	String status () {
		if (this.getResult(this.getResultIdsByPoint("exam25%")[0])[4].equals("1")) { // 25% verilib
			if (this.getResult(this.getResultIdsByPoint("exam25%")[0])[5].equals("fail") || this.BeginingPoint()+Integer.parseInt(this.getResult(this.getResultIdsByPoint("exam25%")[0])[0]) < 51) {
				return "full fail";
			} else {
				return "completed";
			}
		}
		else { //25% verilmiyib
			if (this.getResult(this.getResultIdsByPoint("exam")[0])[4].equals("1")) { // imtahan verilib
				if (this.getResult(this.getResultIdsByPoint("exam")[0])[5].equals("fail") || this.BeginingPoint()+Integer.parseInt(this.getResult(this.getResultIdsByPoint("exam")[0])[0]) < 51) { 
					return "fail";
				}
				else {
					return "complated";
				}
			}
			else { // imtahan vermiyib
				String[] POINTS = this.Points(); 
				for (String i: POINTS) {
					if (this.getResult(this.getResultIdsByPoint(i)[0])[5].equals("fail")) {
							return "full fail";
					}
				}
				for (String i: POINTS) {
					if (this.getResult(this.getResultIdsByPoint(i)[0])[4].equals("1")) {
							return "started";
					}
				}
				return "not started";
			}
		}
	}
	
	String getLastResult () {
		int n;
		if (this.status().equals("not started") ||this.status().equals("started")) {
			return null;
		}
		else if (this.status().equals("fail") || this.status().equals("full fail")) {
			return "F";
		}
		else if (this.BeginingPoint() + Integer.parseInt(this.getResult(this.getResultIdsByPoint("exam")[0])[0]) < 51){
			n = this.BeginingPoint() + Integer.parseInt(this.getResult(this.getResultIdsByPoint("exam25%")[0])[0]);
			
		}
		else {
			n = this.BeginingPoint() + Integer.parseInt(this.getResult(this.getResultIdsByPoint("exam")[0])[0]);
			
		}
		if (n > 90) {
			return "A";
		}
		else if (n > 80) {
			return "B";
		}
		else if (n > 70) {
			return "C";
		}
		else if (n > 60) {
			return "D";
		}
		else  {
			return "E";
		}
	}
	
}
