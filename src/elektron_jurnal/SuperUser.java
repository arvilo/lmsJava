package elektron_jurnal;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

class SuperUser {
	final private Journal journal;
	
	SuperUser (Journal JOURNAL) {
		this.journal = JOURNAL;
	}
	
	private void OperationFailed () {
		System.out.println("Operation Failed...");
	}
	
	int FintoId (String FIN,String table) {
		String query = "select id from " + this.journal.db_name + "." + table + " where fin = '" + FIN +"'";
		try {
			ResultSet rslt = this.journal.statement.executeQuery(query);
			rslt.next();
			return rslt.getInt(1);
		} catch (Exception e){
			OperationFailed();
			return -1;
		}
	}
	
	void addStudent (String FIN,String FIRST_NAME,String LAST_NAME) {
		if (FIN.length() != 7 || FIRST_NAME.length() == 0 || LAST_NAME.length() == 0) {
			OperationFailed();
			return;
		}
		if (!Character.isDigit(FIN.charAt(0))) {
			OperationFailed();
			return;
		}
		for (int i = 0;i < FIN.length();i++) {
			if (!(Character.isUpperCase(FIN.charAt(i)) || Character.isDigit(FIN.charAt(i)))) {
				OperationFailed();
				return;
			}
		}
		for (int i = 0;i < FIRST_NAME.length();i++) {
			if (Character.isLetter(FIRST_NAME.charAt(i))) {
				if (i == 0 && Character.isLowerCase(FIRST_NAME.charAt(i))) {
					OperationFailed();
					return;
				} 
				else if (i != 0 && Character.isUpperCase(FIRST_NAME.charAt(i))) {
					OperationFailed();
					return;
				}
			}
			else {
				OperationFailed();
				return;
			}
		}
		
		for (int i = 0;i < LAST_NAME.length();i++) {
			if (Character.isLetter(LAST_NAME.charAt(i))) {
				if (i == 0 && Character.isLowerCase(LAST_NAME.charAt(i))) {
					OperationFailed();
					return;
				} 
				else if (i != 0 && Character.isUpperCase(LAST_NAME.charAt(i))) {
					OperationFailed();
					return;
				}
			}
			else {
				OperationFailed();
				return;
			}
		}
		try {
			String query = "select fin from " + this.journal.db_name + ".teacher"; 		
			ResultSet rslt = this.journal.statement.executeQuery(query);
			while (rslt.next()) {
				if (rslt.getString(1).equals(FIN)) {
					OperationFailed();
					return;
				}
			}
			query = "insert into " + this.journal.db_name + ".student (fin,first_name,last_name) values ('" + FIN + "','" + FIRST_NAME + "','" + LAST_NAME + "')"; 		
			this.journal.statement.execute(query);
		} catch (Exception e) {
			OperationFailed();
		}
	}
	
	void setStudentBirth (String BIRTH,String FIN) {
		int ID = this.FintoId(FIN, "student");
		String query = "select birth from " + this.journal.db_name + ".student where id = " + ID;
		try {
			ResultSet rslt = this.journal.statement.executeQuery(query);
			rslt.next();
			if (rslt.getString(1) != null) {
				OperationFailed();
				return;
			}
		} catch (Exception e) {
			OperationFailed();
			return;
		}
		
		query = "update " + this.journal.db_name + ".student set birth = '" + BIRTH + "' where id = " + ID;
		try {
			this.journal.statement.execute(query);
		} catch (Exception e) {
			OperationFailed();
			return;
		}
	}
	
	void addGroup (String NAME) {
		if (NAME.indexOf(".") < 1 || NAME.indexOf(".") == NAME.length()-1) {
			OperationFailed();
			return;
		}
		for (int i = 0;i < NAME.length();i++) {
			if (NAME.charAt(i) == '.' && i > NAME.indexOf(".")) {
				OperationFailed();
				return;
			} 
			else if (NAME.charAt(i) != '.' && !(Character.isDigit(NAME.charAt(i)) || Character.isUpperCase(NAME.charAt(i))  )) {
				OperationFailed();
				return;
			}
		}
		String query = "insert into " + this.journal.db_name + ".group_ (number) values ('" + NAME +"')"; 
		try {
			this.journal.statement.execute(query);
		} catch (Exception e) {
			OperationFailed();
			return;
		}
	}
	
	private int GroupNumbertoId (String GroupNumber) {
		String query = "select id from " + this.journal.db_name + ".group_ where number = '" + GroupNumber +"'";
		try {
			ResultSet rslt = this.journal.statement.executeQuery(query);
			rslt.next();
			return rslt.getInt(1);
		} catch (Exception e){
			OperationFailed();
			return -1;
		}
	}
	
	void setGroupSpecialty (String spec,String groupnumber) {
		int ID = this.GroupNumbertoId(groupnumber);
		if (spec.length() == 0) {
			OperationFailed();
			return;
		}
		String query = "select specialty from " + this.journal.db_name + ".group_ where id = " + ID;
		try {
			ResultSet rslt = this.journal.statement.executeQuery(query);
			rslt.next();
			if (rslt.getString(1) != null) {
				OperationFailed();
				return;
			}
		} catch (Exception e) {
			OperationFailed();
			return;
		}
		
		query = "update " + this.journal.db_name + ".group_ set specialty = '" + spec + "' where id = " + ID;
		try {
			this.journal.statement.execute(query);
		} catch (Exception e) {
			OperationFailed();
			return;
		}
	}
	
	void addTeacher (String FIN,String FIRST_NAME,String LAST_NAME) {
		if (FIN.length() != 7 || FIRST_NAME.length() == 0 || LAST_NAME.length() == 0) {
			System.out.println(186);OperationFailed();
			return;
		}
		if (!Character.isDigit(FIN.charAt(0))) {
			System.out.println(190);OperationFailed();
			return;
		}
		for (int i = 0;i < FIN.length();i++) {
			if (!(Character.isUpperCase(FIN.charAt(i)) || Character.isDigit(FIN.charAt(i)))) {
				System.out.println(195);OperationFailed();
				return;
			}
		}
		for (int i = 0;i < FIRST_NAME.length();i++) {
			if (Character.isLetter(FIRST_NAME.charAt(i))) {
				if (i == 0 && Character.isLowerCase(FIRST_NAME.charAt(i))) {
					System.out.println(202);OperationFailed();
					return;
				} 
				else if (i != 0 && Character.isUpperCase(FIRST_NAME.charAt(i))) {
					System.out.println(206);OperationFailed();
					return;
				}
			}
			else {
				System.out.println(211);OperationFailed();
				return;
			}
		}
		
		for (int i = 0;i < LAST_NAME.length();i++) {
			if (Character.isLetter(LAST_NAME.charAt(i))) {
				if (i == 0 && Character.isLowerCase(LAST_NAME.charAt(i))) {
					OperationFailed();
					return;
				} 
				else if (i != 0 && Character.isUpperCase(LAST_NAME.charAt(i))) {
					OperationFailed();
					return;
				}
			}
			else {
				OperationFailed();
				return;
			}
		}
		try {
			String query = "select fin from " + this.journal.db_name + ".student"; 		
			ResultSet rslt = this.journal.statement.executeQuery(query);
			while (rslt.next()) {
				if (rslt.getString(1).equals(FIN)) {
					OperationFailed();
					return;
				}
			}
			query = "insert into " + this.journal.db_name + ".teacher (fin,first_name,last_name) values ('" + FIN + "','" + FIRST_NAME + "','" + LAST_NAME + "')";
			this.journal.statement.execute(query);
		} catch (Exception e) {
			OperationFailed();
		}
	}
	
	void setTeacherBirth (String BIRTH,String FIN) {
		int ID = this.FintoId(FIN, "teacher");
		String query = "select birth from " + this.journal.db_name + ".teacher where id = " + ID;
		try {
			ResultSet rslt = this.journal.statement.executeQuery(query);
			rslt.next();
			if (rslt.getString(1) != null) {
				OperationFailed();
				return;
			}
		} catch (Exception e) {
			OperationFailed();
			return;
		}
		
		query = "update " + this.journal.db_name + ".teacher set birth = '" + BIRTH + "' where id = " + ID;
		try {
			this.journal.statement.execute(query);
		} catch (Exception e) {
			OperationFailed();
			return;
		}
	}
	
	void addStudentGroup (String StudentFin,String group_number) {
		int StudentID = this.FintoId(StudentFin, "student");
		int GroupID = this.GroupNumbertoId(group_number);
		
		if (StudentID == -1 || GroupID == -1) {
			OperationFailed();
			return;
		}
		
		String query = "select student_id from " + this.journal.db_name + ".group__student where group__id =  " + GroupID + " and student_id = " + StudentID;
		try {
			this.journal.connection.setAutoCommit(false);
			ResultSet rslt = this.journal.statement.executeQuery(query);
			while (rslt.next()) {
				this.journal.connection.rollback();
				this.journal.connection.setAutoCommit(true);
				OperationFailed();
				return;
			}
			query = "insert into " + this.journal.db_name + ".group__student (group__id,student_id) values(" + GroupID + "," + StudentID + ")";
			this.journal.statement.execute(query);
			query = "select count(*) from " + this.journal.db_name + ".subject where group__id = " + GroupID;
			rslt = this.journal.statement.executeQuery(query);
			rslt.next();
			int[] Subjects = new int[rslt.getInt(1)];
			query = "select id from " + this.journal.db_name + ".subject where group__id = " + GroupID;
			rslt = this.journal.statement.executeQuery(query);
			int r = 0;
			while (rslt.next()) {
				Subjects[r] = rslt.getInt(1);
				r++;
			}
			
			int number_of_subject_point_id = 0;
			
			for (int i: Subjects) {
				query = "select count(*) from " + this.journal.db_name + ".subject_point where subject_id = " + i;
				rslt = this.journal.statement.executeQuery(query);
				rslt.next();
				number_of_subject_point_id += rslt.getInt(1);
			}
			r = 0;
			int[] Subject_point_ids = new int[number_of_subject_point_id];
			
			for (int i:Subjects) {
				query = "select id from " + this.journal.db_name + ".subject_point where subject_id = " + i;
				rslt = this.journal.statement.executeQuery(query);
				while (rslt.next()) {
					Subject_point_ids[r] = rslt.getInt(1);
					r++;
				}
			}
			
			for (int i: Subject_point_ids) {
				query = "insert into " + this.journal.db_name + ".result (student_id,subject_point_id) values (" + StudentID + "," + i + ")";
				this.journal.statement.execute(query);
			}
			
			this.journal.connection.commit();
			this.journal.connection.setAutoCommit(true);
		} catch (Exception e) {
			try {
				this.journal.connection.rollback();
				this.journal.connection.setAutoCommit(true);
				
			} catch (SQLException e1) {
				OperationFailed();
				e1.printStackTrace();
				return;
			}
			OperationFailed();
			return;
		}
	}

	private int PointNametoId (String name) {
		try {
			String query = "select id from " + this.journal.db_name + ".point where name = '" + name + "'";
			ResultSet rslt = this.journal.statement.executeQuery(query);
			while (rslt.next()) {
				return rslt.getInt(1);
				
			}
			return -1;
		} catch (Exception e) {
			OperationFailed();	
			return -1;
		}
	}
	
	void addSubject (String name,int credit,int hours,String group,String teacher,String[] points_,int[] maxs_,int[] mins_) {
		ResultSet rslt;
		String query;
		try {
			query = "select id from " + this.journal.db_name + ".subject where name = '" + name + "' and group__id = " + this.GroupNumbertoId(group);
			rslt = this.journal.statement.executeQuery(query);
			while (rslt.next()) {
				OperationFailed();
				return;
			}	
		} catch (Exception e) {
				OperationFailed();
				return;
		}
		String[] points = new String[points_.length+2];
		int[] maxs = new int[maxs_.length+2];
		int[] mins = new int[mins_.length+2];
		points[points.length-1] = "exam25%";
		points[points.length-2] = "exam";
		maxs[maxs.length-1] = 50;
		maxs[maxs.length-2] = 50;
		mins[mins.length-1] = 0;
		mins[mins.length-2] = 0;
		for (int i = 0; i < points_.length; i++) {
			points[i] = points_[i];
			maxs[i] = maxs_[i];
			mins[i] = mins_[i];
		}
		int group_id = this.GroupNumbertoId(group);
		int teacher_id = this.FintoId(teacher, "teacher");
		if (group_id == -1 || teacher_id == -1 || name.length() == 0 || credit < 1 || hours < 1 || points.length != maxs.length || points.length != mins.length) {
			OperationFailed();
			return;
		}
		int summax = 0;
		for (int i = 0;i < points.length;i++) {
			if (points[i].length() == 0 || points[i] == null || (i != points.length-2 && points[i].equals("exam")) || (i != points.length-1 && points.equals("exam25%")) || maxs[i] < mins[i] || mins[i] < 0 || maxs[i] < 1) {
				OperationFailed();
				return;
			}
			summax += maxs[i];
		}
		if (summax != 150) {
			OperationFailed();
			return; 
		}
		
		try {
			this.journal.connection.setAutoCommit(false);
			this.journal.connection.commit();
			query = "insert into " + this.journal.db_name + ".subject (name,credit,hours,group__id,teacher_id) values ('" + name + "'," + credit + "," + hours + "," + group_id + "," + teacher_id + ")";
			this.journal.statement.execute(query);
			query = "select last_insert_id()";
			rslt = this.journal.statement.executeQuery(query);
			rslt.next();
			int subject_id = rslt.getInt(1);
			int[] subject_point_ids = new int[points.length];
			for (int i = 0;i < points.length;i++) {
				if (this.PointNametoId(points[i]) == -1) {
					query = "insert into " + this.journal.db_name + ".point (name) values ('" + points[i] + "')";
					this.journal.statement.execute(query);
				}
				query = "insert into " + this.journal.db_name + ".subject_point (subject_id,point_id,max,min) values (" + subject_id + "," + this.PointNametoId(points[i]) + "," + maxs[i] + "," + mins[i] + ")";
				this.journal.statement.execute(query);
				query = "select last_insert_id()";
				rslt = this.journal.statement.executeQuery(query);
				rslt.next();
				subject_point_ids[i] = rslt.getInt(1);
			}
			query =  "select count(*) from " + this.journal.db_name + ".group__student where group__id = " + group_id;
			rslt = this.journal.statement.executeQuery(query);
			rslt.next();
			int number_of_student = rslt.getInt(1);
			query = "select student_id from " + this.journal.db_name + ".group__student where group__id = " + group_id;
			rslt = this.journal.statement.executeQuery(query);
			int[] student_ids = new int[number_of_student];
			for (int i = 0;i < student_ids.length;i++) {
				rslt.next();
				student_ids[i] = rslt.getInt(1);
			}
			
			for (int i: student_ids) {
				for (int j:subject_point_ids) {
					query = "insert into " + this.journal.db_name + ".result (student_id,subject_point_id) values (" + i + "," + j + ")";
					this.journal.statement.execute(query);
				}
			}
			this.journal.connection.commit();
			this.journal.connection.setAutoCommit(true);
		} catch (Exception e) {
			try {
				this.journal.connection.rollback();
				this.journal.connection.setAutoCommit(true);
				OperationFailed();
				return;
			} catch (Exception e1) {
				e1.printStackTrace();
				OperationFailed();
			}
			OperationFailed();
			return;
		}
		
	}
	
	private int GroupSubjecttoid (String GroupNUMBER,String SubjectNAME) {
		try {
			String query = "select id from " + this.journal.db_name + ".subject where group__id = " + this.GroupNumbertoId(GroupNUMBER) + " and name = '" + SubjectNAME + "'";
			ResultSet rslt = this.journal.statement.executeQuery(query);
			while (rslt.next()) {
				return rslt.getInt(1);
			}
			return -1;
		} catch (Exception e) {
			OperationFailed();
			return -1;
		}
	}
	private void updateResult (String GroupNUMBER,String StudentFIN,String SubjectNAME, String point,int amount,String is_changed) {
		int SubjectID = this.GroupSubjecttoid(GroupNUMBER, SubjectNAME);
		String query;
		int subject_point_id, student_id = this.FintoId(StudentFIN, "student"), point_id = this.PointNametoId(point);
		ResultSet rslt;
		int result_id;
		try {
			query = "select id from " + this.journal.db_name + ".subject_point where subject_id = " + SubjectID + " and point_id = " + point_id;
			rslt = this.journal.statement.executeQuery(query);
			subject_point_id = -1;
			while (rslt.next()) {
				subject_point_id = rslt.getInt(1);
			}
			if (subject_point_id == -1) {
				System.out.println(490);OperationFailed();
				return;
			}
			result_id = -1;
			query = "select id from " + this.journal.db_name + ".result where subject_point_id = " + subject_point_id + " and student_id = " + student_id;
			rslt = this.journal.statement.executeQuery(query);
			while (rslt.next()) {
				result_id = rslt.getInt(1);
			}
			if (result_id == -1) {
				System.out.println(500);OperationFailed();
				return;
			}
			
			query = "select is_changed from " + this.journal.db_name + ".result where id = " + result_id;
			rslt = this.journal.statement.executeQuery(query);
			boolean check = true;
			while (rslt.next()) {
				check = false;
				if (!rslt.getString(1).equals(is_changed)) {
					System.out.println(511);OperationFailed();
					return;
				}
			}
			if (check) {
				System.out.println(516);OperationFailed();
				return;
			}
			
			
			query = "select max from " + this.journal.db_name + ".subject_point where id = " + subject_point_id;
			rslt = this.journal.statement.executeQuery(query);
			int max = -1;
			while (rslt.next()) {
				max = rslt.getInt(1);
			}
			if (amount > max || amount < 0) {
				System.out.println(528);OperationFailed();
				return;
			}
		} catch (Exception e) {
			System.out.println(531);OperationFailed();
			return;
		}
		
		try {
			this.journal.connection.setAutoCommit(false);
			query = "update " + this.journal.db_name + ".result set is_changed = true, amount = " + amount + " where id = " + result_id;
			this.journal.statement.execute(query);
			this.journal.connection.commit();
			this.journal.connection.setAutoCommit(true);
		} catch (Exception e) {
			try {
				this.journal.connection.rollback();
				this.journal.connection.setAutoCommit(true);
				System.out.println(544);OperationFailed();
				return;
			} catch (Exception e1) {
				OperationFailed();
				e1.printStackTrace();
				return;
			}
		}
	}
	
	void setResult (String GroupNUMBER,String StudentFIN,String SubjectNAME, String point,int amount) {
		this.updateResult(GroupNUMBER, StudentFIN, SubjectNAME, point, amount, "0");
	}
	
	void changeResult (String GroupNUMBER,String StudentFIN,String SubjectNAME, String point,int amount) {
		this.updateResult(GroupNUMBER, StudentFIN, SubjectNAME, point, amount, "1");
	}
	
	int getResult (String GroupNUMBER,String StudentFIN,String SubjectNAME, String point) {
			StudentSubject ss = new StudentSubject(new Student(this.FintoId(StudentFIN, "student"),this.journal),new Subject(this.GroupSubjecttoid(GroupNUMBER, SubjectNAME),this.journal),this.journal);
			return Integer.parseInt(ss.getResult(ss.getResultIdsByPoint(point)[0])[0]);
	}
	
	String getStudentJournal (String FIN) {
		Student student = new Student(this.FintoId(FIN, "student"),this.journal);
		Group[] groups = student.Groups();
		Subject[] subjects;
		String[] points;
		table Table;
		
		int len = 0;
		for (Group i: groups) {
			len += i.NumberOfSubject();
		}
		
		subjects = new Subject[len];
		len = 0;
		for (Group i: groups) {
			for (Subject j: i.Subjects()) {
				subjects[len] = j;
				len++;
			}
		}
		
		len = 0;
		
		String[] allpoints;
		try {
			String query = "select count(*) from " +  this.journal.db_name + ".point";
			ResultSet rslt = this.journal.statement.executeQuery(query);
			rslt.next();
			allpoints = new String[rslt.getInt(1)];
			query = "select name from " +  this.journal.db_name + ".point";
			rslt = this.journal.statement.executeQuery(query);
			int r = 0;
			while (rslt.next()) {
				allpoints[r] = rslt.getString(1);
				r++;
			}
		} catch (Exception e) {
			OperationFailed();
			return null;
		}
		
		len = 0;
		for (String i: allpoints) {
			overloop:
			for (Subject j: subjects) {
				StudentSubject stsbjtemp = new StudentSubject(student,j,this.journal);
				for (String t: stsbjtemp.Points()) {
					if (t.equals(i)) {
						len++;
						break overloop;
					}
				}
			}
		}
		
		String[] points_ = new String[len];
		points = new String[len];
		len = 0;
		for (String i: allpoints) {
			overloop:
			for (Subject j: subjects) {
				StudentSubject stsbjtemp = new StudentSubject(student,j,this.journal);
				for (String t: stsbjtemp.Points()) {
					if (t.equals(i)) {
						points_[len] = t;
						len++;
						break overloop;
					}
				}
			}
		}
		
		if (points.length != 0) {
			points[points.length-1] = "exam25%";
	
		points[points.length-2] = "exam";
		len = 0;
		for (String i: points_) {
			if (i.equals("exam") || i.equals("exam25%")) {
				continue;
			}
			points[len] = i;
			len++;
		}
		}
		
		Table = new table(subjects.length+1,points.length+3);
		
		Table.datas[0][0] = "N";
		Table.datas[0][1] = "Subject";
		Table.datas[0][Table.datas[0].length - 1] = "Point";
		for (int i = 0;i < points.length;i++) {
			Table.datas[0][i+2] = points[i];
		}
		len = 1;
		for (int i = 0;i < subjects.length;i++) {
			StudentSubject stsb = new StudentSubject(student,subjects[i],this.journal);
			Table.datas[i+1][Table.datas[0].length - 1] = stsb.getLastResult();
			Table.datas[i+1][0] = "" + len;
			len++;
			Table.datas[i+1][1] = stsb.subject.getGroup().getNumber() + " / " + stsb.subject.Name();
			for (int j = 0;j < points.length;j++) {
				for (int t = 0;t < stsb.Points().length;t++) {
					if (points[j].equals(stsb.Points()[t])) {
						if (stsb.getResult(stsb.getResultIdsByPoint(points[j])[0])[4].equals("1")) {
							Table.datas[i+1][j+2] = stsb.getResult(stsb.getResultIdsByPoint(points[j])[0])[0];
							
						} else {
							Table.datas[i+1][j+2] = "d/e";
							
						}
					} 
					
				}
				if (Table.datas[i+1][j+2] == null) {
					Table.datas[i+1][j+2] = "-";
					
					
				}
			}
			
			
		}
		return Table.view();
	}
	
	String[][] getTeacherJournal (String GroupNUMBER,String SubjectNAME) {
		final int subject_id = this.GroupSubjecttoid(GroupNUMBER, SubjectNAME);
		if (subject_id == -1) {
			return null;
		}
		String[][] rtr;
		Group group;
		int group_id = -1;
		try { 
			ResultSet rslt = this.journal.statement.executeQuery("select id from " + this.journal.db_name + ".group_ where number = " + GroupNUMBER);
			while (rslt.next()) {
				group_id = rslt.getInt(1);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		group = new Group(group_id,this.journal);
		Subject subject = new Subject(subject_id,this.journal);
		table Table = new table(group.NumberOfStudent()+1,subject.getPoints().length+2);
		if (group.NumberOfStudent() == 0) {
			rtr = new String[3][1];
		}
		else {
			rtr = new String[3][group.NumberOfStudent()];
		}
		Student[] students = group.Students();
		Table.datas[0][0] = "N";
		Table.datas[0][1] = "Student";
		for (int i = 0;i < subject.getPoints().length;i++) {
			Table.datas[0][i+2] = subject.getPoints()[i];
		}
		for (int i = 0;i < students.length;i++) {
			Table.datas[i+1][0] = (i + 1) + "";
			rtr[1][i] = students[i].Fin();
			Table.datas[i+1][1] = students[i].LastName() + " " + students[i].FirstName();
			for (int j = 0;j < subject.getPoints().length;j++) {
				StudentSubject stsb = new StudentSubject(students[i],subject,this.journal);
				if (stsb.getResult(stsb.getResultIdsByPoint(subject.getPoints()[j])[0])[4].equals("0")) {
					Table.datas[i+1][j+2] = "d/e";
					
				}
				else {
					Table.datas[i+1][j+2] = stsb.getResult(stsb.getResultIdsByPoint(subject.getPoints()[j])[0])[0];
					
				}
			}
		}
		rtr[0][0] = Table.view();
		rtr[2][0] = Table.HTML();
		return rtr;
	}
	
	String getPersonsInfo(String table) {
		table rtr;
		try {
			String query = "select count(*) from " + this.journal.db_name + "." + table;
			ResultSet rslt = this.journal.statement.executeQuery(query);
			int numofstud = -1;
			while (rslt.next()) {
				numofstud = rslt.getInt(1);
			}
			
			query = "select last_name,first_name,fin,birth from " + this.journal.db_name + "." + table + " order by last_name,first_name,fin,birth";
			rslt = this.journal.statement.executeQuery(query);
			rtr = new table(numofstud+1,5);
			rtr.datas[0][0] = "N";
			rtr.datas[0][1] = "Last name";
			rtr.datas[0][2] = "First name";
			rtr.datas[0][3] = "FIN";
			rtr.datas[0][4] = "Birth";
			int n = 1;
			while (rslt.next()) {
				rtr.datas[n][0] = n + "";
				for (int i = 1;i < 5;i++) {
					if (rslt.getString(i) == null) {
						rtr.datas[n][i] = "-";
					}else {
						rtr.datas[n][i] = rslt.getString(i);
					}
					
				}
				n++;
				
			}
			
			return rtr.view();
		} catch (Exception e){
			OperationFailed();
			return null;
		}
	}

	  String GroupInfo (String GroupNumber) {
		  int group_id = this.GroupNumbertoId(GroupNumber);
		  if (group_id == -1) {
			  OperationFailed();
			  return null;
		  }
		  Group group = new Group(group_id,this.journal);
		  Student[] students = group.Students();
		  table rtr = new table(students.length+1,5);
		  rtr.datas[0][0] = "N";
		  rtr.datas[0][1] = "Last name";
		  rtr.datas[0][2] = "First name";
		  rtr.datas[0][3] = "FIN";
		  rtr.datas[0][4] = "Birth";
		  for (int i = 1;i < rtr.datas.length;i++) {
			  rtr.datas[i][0] = i + "";
			  rtr.datas[i][1] = students[i-1].LastName();
			  rtr.datas[i][2] = students[i-1].FirstName();
			  rtr.datas[i][3] = students[i-1].Fin();
			  if (students[i-1].Birth() == null) {
				  rtr.datas[i][4] = "-";
			  }
			  else {
				  rtr.datas[i][4] = students[i-1].Birth();
			  }
		  }
		  return rtr.view();
	  }

	  String Groups () {
		  try {
			  Group[] groups;
			  String query = "select count(*) from " + this.journal.db_name +".group_";
			  ResultSet rslt = this.journal.statement.executeQuery(query);
			  groups = null;
			  table Table = null;
			  while (rslt.next()) {
				  groups = new Group[rslt.getInt(1)];
				  Table = new table(rslt.getInt(1)+1,3);
			  }
			  
			  if (groups == null) {
				  OperationFailed();
				  return null;
			  }
			  query = "select id from " + this.journal.db_name + ".group_ order by number";
			  rslt  = this.journal.statement.executeQuery(query);
			  Table.datas[0][0] = "Group number";
			  Table.datas[0][1] = "Spectiality";
			  Table.datas[0][2] = "Students";
			  if (groups.length == 0) {
				  return Table.view();
			  }
			  int[] ids = new int[groups.length];
			  for (int i = 0;i < groups.length;i++) {
				  rslt.next();
				  ids[i] = rslt.getInt(1);
			  }
			  
			  for (int i = 0;i < groups.length;i++) {
				  groups[i] = new Group(ids[i],this.journal);
				  Table.datas[i+1][0] = groups[i].getNumber();
				  Table.datas[i+1][2] = groups[i].NumberOfStudent() + "";
				  if (groups[i].Specialty() == null) {
					  Table.datas[i+1][1] = "-";
				  }
				  else{
					  Table.datas[i+1][1] = groups[i].Specialty();
				  }
			  }
			  return Table.view();
		  } catch (Exception e) {
			  e.printStackTrace();
			  OperationFailed();
			  return null;
		  }
	  }

	String getSubjects (String group_number) {
		Group group =  new Group(this.GroupNumbertoId(group_number),this.journal);
		int numofsub = group.NumberOfSubject();
		table Table = new table(numofsub+1,2);
		Table.datas[0][0] = "Subject";
		Table.datas[0][1] = "Tacher";
		if (group.Subjects().length == 0) {
			return Table.view();
		}
		for (int i = 1;i < Table.datas.length;i++) {
			Table.datas[i][0] = group.Subjects()[i-1].Name();
			Teacher teacher = group.Subjects()[i-1].getTeacher();
			Table.datas[i][1] = teacher.LastName() + " " + teacher.FirstName();
		}
		return Table.view();
	}

	String showUsers () {
		try {
			String query = "select count(*) from " + this.journal.db_name + ".user";
			ResultSet rslt = this.journal.statement.executeQuery(query);
			table Table = null;
			while (rslt.next()) {
				Table = new table(rslt.getInt(1)+1,3);
			}
			query = "select id from " + this.journal.db_name + ".user order by username";
			rslt = this.journal.statement.executeQuery(query);
			int[] ids = new int[Table.datas.length-1];
			int r = 0;
			while (rslt.next()) {
				ids[r] = rslt.getInt(1);
				r++;
			}
			
			Table.datas[0][0] = "Username";
			Table.datas[0][1] = "Type";
			Table.datas[0][2] = "Owner";
			for (int i = 0;i < ids.length;i++) {
				Table.datas[i+1][0] = this.journal.Data("user", ids[i], "username");
				Table.datas[i+1][1] = this.journal.Data("user", ids[i], "type");
				int owner_id = -1;
				if (this.journal.Data("user", ids[i], "owner_id") == null) {
					Table.datas[i+1][2] = "-";
				}
				else {
					owner_id = Integer.parseInt(this.journal.Data("user", ids[i], "owner_id"));
				}
				if (Table.datas[i+1][2] == "-") {
					
				}
				else if (Table.datas[i+1][1].equals("student")) {
					Table.datas[i+1][2] = this.journal.Data("student", owner_id, "first_name") + " " + this.journal.Data("student", Integer.parseInt(this.journal.Data("user", ids[i], "owner_id")), "last_name") + "(" + this.journal.Data("student", Integer.parseInt(this.journal.Data("user", ids[i], "owner_id")), "fin") + ")";
				}
				else if (Table.datas[i+1][1].equals("teacher")) {
					Table.datas[i+1][2] = this.journal.Data("teacher", Integer.parseInt(this.journal.Data("user", ids[i], "owner_id")), "first_name") + " " + this.journal.Data("teacher", Integer.parseInt(this.journal.Data("user", ids[i], "owner_id")), "last_name") + "(" + this.journal.Data("teacher", Integer.parseInt(this.journal.Data("user", ids[i], "owner_id")), "fin") + ")";
					
				}
			}
			return Table.view();
		} catch (Exception e) {
			e.printStackTrace();
			OperationFailed();
			return null;
		}
	}
	
	void createUser(String username,String type,int owner,String pass) {
		if (username.length() == 0 || pass.length() == 0) {
			OperationFailed();
			return;
		}
		try {
			if (!type.equals("admin")) {
				String query = "insert into " + this.journal.db_name + ".user (username,type,owner_id,pass) values ('" + username + "','" + type + "'," + owner + ",'" + pass + "')";
				this.journal.statement.execute(query);
			
			}
			else {
				this.journal.statement.execute("insert into " + this.journal.db_name + ".user (username,type,pass) values ('" + username + "','" + type + "','" + pass + "')");
			}
		} catch (Exception e) {
			OperationFailed();
			return;
		}
	}
	
	int getOwnerIdbyUserid (int id) {
		return Integer.parseInt(this.journal.Data("user", id, "owner_id"));
	}
	
	String[] getTeacherSubjects(String FIN) {
		int id = this.FintoId(FIN, "teacher");
		try {
			String query = "select count(*) from subject where teacher_id = " + id;
			ResultSet rslt = this.journal.statement.executeQuery(query);
			rslt.next();
			table Table = new table(rslt.getInt(1)+1,2);
			String[] ids = new String[rslt.getInt(1)+2];
			Table.datas[0][1] = "subject";
			Table.datas[0][0] = "N";
			
			query = "select concat(" + this.journal.db_name + ".group_.number,concat('/'," + this.journal.db_name +".subject.name)) as subject,subject.id from "+ this.journal.db_name + ".subject join " + this.journal.db_name + ".group_ on " + this.journal.db_name + ".group_.id=" + this.journal.db_name + ".subject.group__id where teacher_id = " + this.FintoId(FIN, "teacher");
			rslt = this.journal.statement.executeQuery(query);
			int r = 1;
			while (rslt.next()) {
				ids[r] = rslt.getString(2);
				Table.datas[r][1] = rslt.getString(1);
				Table.datas[r][0] = r+"";
				
				r++;
			}
			ids[0] = Table.view();
			ids[ids.length-1] = Table.HTMLteachergroups();
			return ids;
		} catch (Exception e) {
			e.printStackTrace();
			OperationFailed();
			return null;
		}
	}
	
	void resetUserPassword(String username, String new_pass) {
		try {
			if (username.equals("admin")) {
				OperationFailed();
				return;
			}
			String query = "update " + this.journal.db_name + ".user set reseted = true, pass = '" + new_pass + "' where username = '" + username + "'"; 
			this.journal.statement.execute(query);
		} catch (Exception e) {
			OperationFailed();
			return;
		}
	}
	
	void changeUserPassword(String username, String new_pass) {
		try {
			String query = "update " + this.journal.db_name + ".user set reseted = false, pass = '" + new_pass + "' where username = '" + username + "'"; 
			this.journal.statement.execute(query);
		} catch (Exception e) {
			OperationFailed();
			return;
		}
	}
	
	double getUOMG(String FIN) {
		int stdID = this.FintoId(FIN, "student");
		if (stdID == -1) {
			return -1;
		}
		Student student = new Student(stdID,this.journal);
		double credit = 0,summ = 0;
		for (Group i:student.Groups()) {
			for (Subject j: i.Subjects()) {
				StudentSubject stsb = new StudentSubject(student,j,this.journal);
				if (!(stsb.status().equals("started") || stsb.status().equals("not started"))) {
					if (!(stsb.status().equals("fail") || stsb.status().equals("full fail"))){
						String apart_from;
						if (stsb.getResult(stsb.getResultIdsByPoint("exam25%")[0])[4].equals("1"))  {
							apart_from = "exam";
						}
						else {
							apart_from = "exam25%";
						}
						for (String k:stsb.Points()) {
							if (!k.equals(apart_from)) {
								summ += (double)Integer.parseInt(stsb.getResult(stsb.getResultIdsByPoint(k)[0])[0]) * (double)stsb.subject.credit();
							}
						}
					}
					credit += (double)stsb.subject.credit();
				}
			}
		}
		
		return Math.round((summ / credit)*100)/100;
		
	}
	
	void updateFile(String filePath, String content) {
		 
		//System.out.println(content);
			 try {
	            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
	            writer.write(content);
	            writer.close();
	         } catch (IOException e) {}
	}
	
	String getmmadminContent () {
		return "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>" + this.journal.db_name + "</title><link rel=\"stylesheet\" href=\"style.css\"></head><body><div class=\"container\"><a href=\"all_students.html\" class=\"button\">Students</a><a href=\"all_teachers.html\" class=\"button\">Teachers</a></div></body></html>";
	}
	
	String getstyleContent () {
		return "body { margin: 0; padding: 0; background-image: url('arka_fon.jpg'); background-size: cover; background-repeat: no-repeat; background-attachment: fixed; background-position: center; background-image: url('background.jpg'); } .container { position: relative; width: 100%; height: 100vh; background-size: cover; background-repeat: no-repeat; background-image: url('background.jpg'); background-attachment: fixed; display: flex; flex-direction: column; justify-content: center; align-items: center; background-position: center; } h1 { font-size: 24px; margin: 10; color: rgb(137, 180, 180); } a { text-decoration: none; display: block; padding: 10px; font-size: 24px; color: #333; margin: 10px; text-align: center; border: 2px solid #333; border-radius: 5px; transition: background-color 0.3s, color 0.3s; background-color: white; } a:hover { background-color: #333; color: #fff; } .ahr { all: revert; text-decoration: none ; } .button { text-decoration: none; display: inline-block; padding: 10px 20px; font-size: 18px; background-color: rgb(101, 97, 119); color: #fff; border: none; border-radius: 5px; transition: background-color 0.3s, color 0.3s, transform 0.2s ease-in-out; } .button:hover { background-color: #555; color: #fff; transform: scale(1.25); } html, body { height: 100%; } body { margin: 0; background: linear-gradient(45deg, #49a09d, #5f2c82); font-family: sans-serif; font-weight: 100; } .container { position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); } table { width: 800px; border-collapse: collapse; overflow: hidden; box-shadow: 0 0 20px rgba(0,0,0,0.1); margin: 15px; } th, td { padding: 15px; background-color: rgba(255,255,255,0.2); color: #fff; } th { text-align: left; } thead th { background-color: #55608f; } tbody tr { &:hover { background-color: rgba(255,255,255,0.3); } } tbody td { position: relative; &:hover { &:before { content: \"\"; position: absolute; left: 0; right: 0; top: -9999px; bottom: -9999px; background-color: rgba(255,255,255,0.2); z-index: -1; } } } .ahr { color: #fff; } h1 { text-align: center; font-size: 24px; margin-top: 20px; }";
	}
	
	String getall_studentsContent () {
		String rtr1 = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>Document</title><link rel=\"stylesheet\" href=\"style.css\"></head><body>";
		table rtr;
		try {
			String query = "select count(*) from " + this.journal.db_name + ".student";
			ResultSet rslt = this.journal.statement.executeQuery(query);
			int numofstud = -1;
			while (rslt.next()) {
				numofstud = rslt.getInt(1);
			}
			
			query = "select last_name,first_name,fin,birth from " + this.journal.db_name + ".student order by last_name,first_name,fin,birth";
			rslt = this.journal.statement.executeQuery(query);
			rtr = new table(numofstud+1,5);
			rtr.datas[0][0] = "N";
			rtr.datas[0][1] = "Last name";
			rtr.datas[0][2] = "First name";
			rtr.datas[0][3] = "FIN";
			rtr.datas[0][4] = "Birth";
			int n = 1;
			while (rslt.next()) {
				rtr.datas[n][0] = n + "";
				for (int i = 1;i < 5;i++) {
					if (rslt.getString(i) == null) {
						rtr.datas[n][i] = "-";
					}else {
						rtr.datas[n][i] = rslt.getString(i);
					}
					
				}
				n++;}
				return rtr1+rtr.HTMLwithateg() + "</body></html>";
			} catch (Exception e){
				OperationFailed();
				return null;
			}
	}
	
	String getall_teachersContent () {
		String rtr1 = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>Document</title><link rel=\"stylesheet\" href=\"style.css\"></head><body>";
		table rtr;
		try {
			String query = "select count(*) from " + this.journal.db_name + ".teacher";
			ResultSet rslt = this.journal.statement.executeQuery(query);
			int numofstud = -1;
			while (rslt.next()) {
				numofstud = rslt.getInt(1);
			}
			
			query = "select last_name,first_name,fin,birth from " + this.journal.db_name + ".teacher order by last_name,first_name,fin,birth";
			rslt = this.journal.statement.executeQuery(query);
			rtr = new table(numofstud+1,5);
			rtr.datas[0][0] = "N";
			rtr.datas[0][1] = "Last name";
			rtr.datas[0][2] = "First name";
			rtr.datas[0][3] = "FIN";
			rtr.datas[0][4] = "Birth";
			int n = 1;
			while (rslt.next()) {
				rtr.datas[n][0] = n + "";
				for (int i = 1;i < 5;i++) {
					if (rslt.getString(i) == null) {
						rtr.datas[n][i] = "-";
					}else {
						rtr.datas[n][i] = rslt.getString(i);
					}
					
				}
				n++;}
				return rtr1+rtr.HTMLwithateg() + "</body></html>";
			} catch (Exception e){
				OperationFailed();
				return null;
			}
	}
	
	String studentjournalContent (String FIN) {
		Student student = new Student(this.FintoId(FIN, "student"),this.journal);
		String rtr1 = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>Document</title><link rel=\"stylesheet\" href=\"style.css\"></head><body><h1>" + student.FirstName() + " " + student.LastName() + "</h1><h1>UOMG: " + this.getUOMG(FIN) + "</h1>";
		//Student student = new Student(this.FintoId(FIN, "student"),this.journal);
		Group[] groups = student.Groups();
		Subject[] subjects;
		String[] points;
		table Table;
		
		int len = 0;
		for (Group i: groups) {
			len += i.NumberOfSubject();
		}
		
		subjects = new Subject[len];
		len = 0;
		for (Group i: groups) {
			for (Subject j: i.Subjects()) {
				subjects[len] = j;
				len++;
			}
		}
		
		len = 0;
		
		String[] allpoints;
		try {
			String query = "select count(*) from " +  this.journal.db_name + ".point";
			ResultSet rslt = this.journal.statement.executeQuery(query);
			rslt.next();
			allpoints = new String[rslt.getInt(1)];
			query = "select name from " +  this.journal.db_name + ".point";
			rslt = this.journal.statement.executeQuery(query);
			int r = 0;
			while (rslt.next()) {
				allpoints[r] = rslt.getString(1);
				r++;
			}
		} catch (Exception e) {
			OperationFailed();
			return null;
		}
		
		len = 0;
		for (String i: allpoints) {
			overloop:
			for (Subject j: subjects) {
				StudentSubject stsbjtemp = new StudentSubject(student,j,this.journal);
				for (String t: stsbjtemp.Points()) {
					if (t.equals(i)) {
						len++;
						break overloop;
					}
				}
			}
		}
		
		String[] points_ = new String[len];
		points = new String[len];
		len = 0;
		for (String i: allpoints) {
			overloop:
			for (Subject j: subjects) {
				StudentSubject stsbjtemp = new StudentSubject(student,j,this.journal);
				for (String t: stsbjtemp.Points()) {
					if (t.equals(i)) {
						points_[len] = t;
						len++;
						break overloop;
					}
				}
			}
		}
		
		if (points.length != 0) {
			points[points.length-1] = "exam25%";
	
		points[points.length-2] = "exam";
		len = 0;
		for (String i: points_) {
			if (i.equals("exam") || i.equals("exam25%")) {
				continue;
			}
			points[len] = i;
			len++;
		}
		}
		
		Table = new table(subjects.length+1,points.length+3);
		
		Table.datas[0][0] = "N";
		Table.datas[0][1] = "Subject";
		Table.datas[0][Table.datas[0].length - 1] = "Point";
		for (int i = 0;i < points.length;i++) {
			Table.datas[0][i+2] = points[i];
		}
		len = 1;
		for (int i = 0;i < subjects.length;i++) {
			StudentSubject stsb = new StudentSubject(student,subjects[i],this.journal);
			Table.datas[i+1][Table.datas[0].length - 1] = stsb.getLastResult();
			Table.datas[i+1][0] = "" + len;
			len++;
			Table.datas[i+1][1] = stsb.subject.getGroup().getNumber() + " / " + stsb.subject.Name();
			for (int j = 0;j < points.length;j++) {
				for (int t = 0;t < stsb.Points().length;t++) {
					if (points[j].equals(stsb.Points()[t])) {
						if (stsb.getResult(stsb.getResultIdsByPoint(points[j])[0])[4].equals("1")) {
							Table.datas[i+1][j+2] = stsb.getResult(stsb.getResultIdsByPoint(points[j])[0])[0];
							
						} else {
							Table.datas[i+1][j+2] = "d/e";
							
						}
					} 
					
				}
				if (Table.datas[i+1][j+2] == null) {
					Table.datas[i+1][j+2] = "-";
					
					
				}
			}
			
			
		}
		return rtr1 + Table.HTML() + "</body></html>";
	}
	
	String[] getAllFINs(String table) {
		try {
			String query = "select count(*) from " + this.journal.db_name + "." + table;
			ResultSet rslt = this.journal.statement.executeQuery(query);
			String[] rtr = null;
			while (rslt.next()) {
				rtr = new String[rslt.getInt(1)];
			}
			query = "select fin from " + this.journal.db_name + "." + table;
			rslt = this.journal.statement.executeQuery(query);
			int r = 0;
			while (rslt.next()) {
				rtr[r] = rslt.getString(1);
				r++;
			}
			return rtr;
		} catch (Exception e) {
			OperationFailed();
			return null;
		}
	}
	
	String getTeacherGroupsContent (String FIN) {
		Teacher tc = new Teacher(this.FintoId(FIN, "teacher"),this.journal);
		String rtr1 = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>Document</title><link rel=\"stylesheet\" href=\"style.css\"></head><body><h1>" + tc.FirstName() + " " + tc.LastName() + "</h1>";
		rtr1 += this.getTeacherSubjects(FIN)[this.getTeacherSubjects(FIN).length-1];
		rtr1 += "</body></html>";
		return rtr1;
	}
	
	Group[] allgroups () {
		try {
			  Group[] groups;
			  String query = "select count(*) from " + this.journal.db_name +".group_";
			  ResultSet rslt = this.journal.statement.executeQuery(query);
			  groups = null;
			  while (rslt.next()) {
				  groups = new Group[rslt.getInt(1)];
			  }
			  
			  if (groups == null) {
				  OperationFailed();
				  return null;
			  }
			  query = "select id from " + this.journal.db_name + ".group_ order by number";
			  rslt  = this.journal.statement.executeQuery(query);
			  
			  int[] ids = new int[groups.length];
			  for (int i = 0;i < groups.length;i++) {
				  rslt.next();
				  ids[i] = rslt.getInt(1);
			  }
			  
			  for (int i = 0;i < groups.length;i++) {
				  groups[i] = new Group(ids[i],this.journal);
				  
			  }
			  return groups;
		} catch (Exception e) {
			OperationFailed();
			return null;
		}
	}
	
	String getTeacherjournalContent(String GroupNUMBER,String SubjectNAME) {
		String rtr1 = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>Document</title><link rel=\"stylesheet\" href=\"style.css\"></head><body><h1>" + GroupNUMBER + "</h1><h1>" + SubjectNAME + "</h1>";
		return rtr1 + this.getTeacherJournal(GroupNUMBER, SubjectNAME)[2][0] + "</body></html>";
	}
	
 }
