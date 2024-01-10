package elektron_jurnal;

import java.sql.ResultSet;
import java.util.Scanner;

public class Session {
	final private Journal journal = new Journal("localhost",3306,"root","Aa123456","test_jr_v1");;
	final private String PATH = "C:/Users/Nicat/Documents/java_journal";
	private int type;   // 0 - admin, 1 - teacher, 2 - student
	private int user_id = -1;
	private IPerson owner;
	final Scanner scan = new Scanner(System.in);
	final SuperUser user = new SuperUser(journal);
	public Session () {
		while (true) {
			System.out.print("Username: ");
			String username = scan.nextLine();
			System.out.print("Password: ");
			String password = scan.nextLine();
			try {
				String query = "select id from " + this.journal.db_name + ".user where username = '" + username + "'";
				ResultSet rslt = this.journal.statement.executeQuery(query);
				while (rslt.next()) {
					user_id = rslt.getInt(1);
				}
				if (user_id == -1) {
					System.out.println("username couldn't find, please again\n");
				}
				else if (this.journal.Data("user", user_id, "pass").equals(password)) {
					if (this.journal.Data("user", user_id, "type").equals("admin")) {
						this.type = 0;
					}
					else if (this.journal.Data("user", user_id, "type").equals("teacher")) {
						this.type = 1;
					} 
					else {
						this.type = 2;
					}
					if (this.journal.Data("user", user_id, "reseted").equals("1")) {
						System.out.println("Your password has been reset");
						System.out.print("New password: ");
						String pass = scan.nextLine();
						if (pass.equals("")) {
							OperationFailed();
							continue;
						}
						query = "update " + this.journal.db_name + ".user set reseted = 0, pass = '" + pass + "' where username = '" + username + "'";
						this.journal.statement.execute(query);
					}
					this.MainMenu(0,null);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		updateFiles();
	}
	
	
	private void OperationFailed () {
		System.out.println("Operation failed...");
	}
	private int chsng(String arg) {
		if (arg.length() == 0) {
			OperationFailed();
			return -1;
		} 
		else {
			for (int i = 0;i < arg.length();i++) {
				try{
					if (!Character.isDigit(arg.charAt(i))) {
						OperationFailed();
						return -1;
					}
				} catch (Exception e) {
					OperationFailed();
					return -1;
				}
			}
			return Integer.parseInt(arg);
		}
	}
	
	
	
	private void MainMenu(int n,String[] input) {
		//System.out.println(n);
		switch (n) {
			case 0:
				switch (this.type) {
					case 0:
						System.out.print("1)Students\n2)Teachers\n3)Groups\n4)Users\n5)Change password\n");
						switch (chsng(scan.nextLine())) {
							case 0:
								return;
							case 1:
								MainMenu(1,null);
								break;
							case 2:
								MainMenu(2,null);
								break;
							case 3:
								MainMenu(7,null);
								break;
							case 4:
								MainMenu(14,null);
								break;
							case 5:
								MainMenu(16,null);
								break;
							default:
								MainMenu(0,null);
								break;
						}
						break;
					case 1:
						owner = new Teacher(user.getOwnerIdbyUserid(user_id),this.journal);
						System.out.println(owner.FirstName() + " " + owner.LastName()+"\nFIN: " + owner.Fin());
						String[] ids = user.getTeacherSubjects(owner.Fin());
						System.out.println(ids[0]);
						System.out.print("Subject(N): ");
						String[] values = new String[1];
						values[0] = scan.nextLine();
						if (!values[0].equals("0")) {							
							values[0] = ids[Integer.parseInt(values[0])];
							MainMenu(15,values);
						}
						break;
					case 2:
						owner = new Student(user.getOwnerIdbyUserid(user_id),this.journal);
						System.out.println(owner.FirstName() + " " + owner.LastName() + "     UOMG: " + user.getUOMG(owner.Fin()));
						System.out.println(user.getStudentJournal(owner.Fin()));
						scan.nextLine();
						break;
				}
				break;
			case 1:
				System.out.println(user.getPersonsInfo("student"));
				System.out.print("\n1)Set student birth\n2)Add student\n");
				switch (chsng(scan.nextLine())) {
					case 0:
						MainMenu(0,null);
						break;
					case 1:
						System.out.print("FIN: ");
						String FIN = scan.nextLine();
						System.out.print("Year: ");
						String year = scan.nextLine();
						System.out.print("Month: ");
						String month = scan.nextLine();
						System.out.print("Day: ");
						String day = scan.nextLine();
						user.setStudentBirth(year+"-"+month+"-"+day, FIN);
						MainMenu(1,null);
						break;
					case 2:
						MainMenu(9,null);
						break;
					default:
						OperationFailed();
						MainMenu(1,null);
				}
				break;
			case 2:
				System.out.println(user.getPersonsInfo("teacher"));
				System.out.print("\n1)Set teacher birth\n2)Add teacher\n");
				switch (chsng(scan.nextLine())) {
					case 0:
						MainMenu(0,null);
						break;
					case 1:
						System.out.print("FIN: ");
						String FIN = scan.nextLine();
						System.out.print("Year: ");
						String year = scan.nextLine();
						System.out.print("Month: ");
						String month = scan.nextLine();
						System.out.print("Day: ");
						String day = scan.nextLine();
						user.setTeacherBirth(year+"-"+month+"-"+day, FIN);
						MainMenu(2,null);
						break;
					case 2:
						MainMenu(10,null);
						break;
					default:
						OperationFailed();
						MainMenu(1,null);
				}
				break;
			case 3:
				System.out.print("Group: ");
				String input_group = scan.nextLine();
				if (input_group.equals("0")) {
					MainMenu(7,null);
				}
				if (user.GroupInfo(input_group) == null) {
					MainMenu(3,null);
				}
				String[] values = new String[1];
				values[0] = input_group;
				System.out.println(values[0]);
				MainMenu(5,values);
				break;
			case 4:
				try{ 
				System.out.print("Student(N): ");
				String FIN = user.getTeacherJournal(input[0], input[1])[1][Integer.parseInt(scan.nextLine())-1];
				System.out.print("Point: ");
				String point = scan.nextLine();
				System.out.print("Amount: ");
				String amount = scan.nextLine();
				user.changeResult(input[0], FIN, input[1], point, Integer.parseInt(amount));
				MainMenu(6,input);
				} catch (Exception e) {
					e.printStackTrace();
					OperationFailed();
					MainMenu(3,null);
					break;
				}
				break;
			case 5:
				String group = input[0];
				System.out.println("Group: " + input[0]);
				System.out.println(user.GroupInfo(group));
				System.out.println(user.getSubjects(group));
				System.out.print("Subject: ");
				String subject_name = scan.nextLine();
				if (subject_name.equals("0")) {
					MainMenu(7,null);
					break;
				}
				String[] vls = new String[2];
				vls[0] = input[0];
				vls[1] = subject_name;
				if (user.getTeacherJournal(vls[0], vls[1]) == null) {
					MainMenu(5,input);
					break;
				}
				MainMenu(6,vls);
				break;
			case 6:
				System.out.println("Group: " + input[0] + "     " + "Subejct: " + input[1]);
				System.out.println(user.getTeacherJournal(input[0], input[1])[0][0]);
				System.out.print("1)Change point\n");
				switch (this.chsng(scan.nextLine())) {
					case 0:
						String[] vle5 = new String[1];
						vle5[0] = input[0];
						MainMenu(5,vle5);
						break;
					case 1:
						String[] inpt = new String[2];
						inpt[0] = input[0];
						inpt[1] = input[1];
						MainMenu(4,inpt);
						break;
					default:
						MainMenu(3,null);
						break;
				}
				break;
			case 7:	
				System.out.print(user.Groups()+"\n1)Set group spectiality\n2)Choose Group\n3)Create group\n4)Add student to group\n5)Create subject\n");
				
				switch (this.chsng(scan.nextLine())) {
					case 0:
						MainMenu(0,null);
						break;
					case 1:
						MainMenu(8,null);
						break;
					case 2:
						MainMenu(3,null);
						break;
					case 3:
						MainMenu(11,null);
						break;
					case 4:
						MainMenu(12,null);
						break;
					case 5:
						MainMenu(13,null);
						break;
					default:
						MainMenu(7,input);
						break;
				}
				break;
			case 8:
				System.out.print("Group: ");
				String Group = scan.nextLine();
				System.out.print("Spectiality: ");
				String spec = scan.nextLine();
				try {
					user.setGroupSpecialty(spec ,Group);
					MainMenu(7,null);
					break;
				} catch (Exception e) {
					OperationFailed();
					MainMenu(7,null);
					break;
				}
			case 9:
				System.out.print("First name: ");
				String first_name = scan.nextLine();
				System.out.print("Last name: ");
				String last_name = scan.nextLine();
				System.out.print("FIN: ");
				String FIN = scan.nextLine();
				user.addStudent(FIN, first_name, last_name);
				MainMenu(1,null);
				break;
			case 10:
				System.out.print("First name: ");
				String First_name = scan.nextLine();
				System.out.print("Last name: ");
				String Last_name = scan.nextLine();
				System.out.print("FIN: ");
				String Fin = scan.nextLine();
				user.addTeacher(Fin, First_name, Last_name);
				MainMenu(2,null);
				break;
			case 11:
				System.out.print("Group number: ");
				user.addGroup(scan.nextLine());
				MainMenu(7,null);
				break;
			case 12:
				System.out.print("Group number: ");
				String grp = scan.nextLine();
				System.out.print("Student FIN: ");
				String std_fin = scan.nextLine();
				user.addStudentGroup(std_fin, grp);
				MainMenu(7,null);
				break;
			case 13:
				System.out.print("Group: ");
				String g = scan.nextLine();
				System.out.print("Subject name: ");
				String subj = scan.nextLine();
				System.out.print("Teacher FIN: ");
				String f = scan.nextLine();
				System.out.print("Credit: ");
				int credit = Integer.parseInt(scan.nextLine());
				System.out.print("Hours: ");
				int hours = Integer.parseInt(scan.nextLine());
				System.out.print("Number of points: ");
				String[] points_ = new String[Integer.parseInt(scan.nextLine())];
				int[] maxs_ = new int[points_.length],mins_ = new int[points_.length];
				for (int i = 0; i < points_.length;i++) {
					System.out.print("Name of point " + (i + 1) +": ");
					points_[i] = scan.nextLine();
					System.out.print("Maximum of " + points_[i] + ": ");
					maxs_[i] = Integer.parseInt(scan.nextLine());
					System.out.print("Minimum of " + points_[i] + ": ");
					mins_[i] = Integer.parseInt(scan.nextLine());
				}
				user.addSubject(subj, credit, hours, g, f, points_, maxs_, mins_);
				MainMenu(7,null);
				break;
			case 14:
				System.out.println(user.showUsers());
				System.out.print("1)Create user\n2)Reset password\n");
				switch (this.chsng(scan.nextLine())) {
					case 0:
						MainMenu(0,null);
						break;
					case 1:
						System.out.print("Username: ");
						String usr = scan.nextLine();
						System.out.print("Type: ");
						String type = scan.nextLine();
						int owner_id = -1;
						if (type.equals("teacher")) {
							System.out.print("Owner FIN: ");
							String owner = scan.nextLine();
							owner_id = user.FintoId(owner, "teacher");
						}
						else if (type.equals("student")) {
							System.out.print("Owner FIN: ");
							String owner = scan.nextLine();
							owner_id = user.FintoId(owner, "student");
						}
						
						System.out.print("Password: ");
						String pass = scan.nextLine();
						user.createUser(usr, type, owner_id, pass);
						MainMenu(14,null);
						break;
					case 2:
						System.out.print("Username: ");
						String username = scan.nextLine();
						System.out.print("New password: ");
						String new_pass = scan.nextLine();
						user.resetUserPassword(username, new_pass);
						MainMenu(14,null);
						break;
					default:
						MainMenu(14,null);
						break;
				}
				break;
			case 15:
				Subject subject = new Subject(Integer.parseInt(input[0]),this.journal); 
				String[][] result = user.getTeacherJournal(subject.getGroup().getNumber(), subject.Name());
				System.out.println("Group: " + subject.getGroup().getNumber() + "     Subject: " + subject.Name());
				System.out.println(result[0][0]);
			    System.out.println("1)Set result");
			    switch (this.chsng(scan.nextLine())) {
			    	case 0:
			    		MainMenu(0,null);
			    		break;
			    	case 1:
			    		System.out.print("Student(N): ");
			    		String n2 = scan.nextLine();
			    		System.out.print("Point: ");
			    		String point = scan.nextLine();
			    		System.out.print("Amount: ");
			    		int amount = Integer.parseInt(scan.nextLine());
			    		user.setResult(subject.getGroup().getNumber(), result[1][Integer.parseInt(n2)-1], subject.Name(), point, amount);
			    		MainMenu(15,input);
			    		break;
			    	default:
			    		MainMenu(15,input);
			    		break;
			    }
			    
			case 16:
				System.out.print("New password: ");
				String pass = scan.nextLine();
				user.changeUserPassword(this.journal.Data("user", user_id, "username"), pass);
				MainMenu(0,null);
				break;
			default:
				MainMenu(0,null);
		}
	}
	
	private void updateFiles() {
		user.updateFile(PATH+"/style.css", user.getstyleContent());
		user.updateFile(PATH+"/mmadmin.html", user.getmmadminContent());
	    user.updateFile(PATH+"/all_students.html", user.getall_studentsContent());
	    user.updateFile(PATH+"/all_teachers.html", user.getall_teachersContent());
	    for (String i: user.getAllFINs("student")) {
	    	user.updateFile(PATH+"/"+i+".html", user.studentjournalContent(i));
	    }
	    
	    for (String i: user.getAllFINs("teacher")) {
	    	user.updateFile(PATH+"/"+i+".html", user.getTeacherGroupsContent(i));
	    }
	    
	    for (Group i: user.allgroups()) {
	    	String fkgroupnmbr = "";
    		for (int k = 0;k < i.getNumber().length();k++) {
    			if (i.getNumber().charAt(k) == '.') {
    				fkgroupnmbr += "_";
    			}
    			else {
    				fkgroupnmbr += i.getNumber().charAt(k);
    			}
    		}
	    	for (Subject j: i.Subjects()) {
	    		String fksubject = "";
	    		for (int k = 0;k < j.Name().length();k++) {
	    			if (j.Name().charAt(k) == ' ') {
	    				fksubject += "_";
	    			}
	    			else {
	    				fksubject += j.Name().charAt(k);
	    			}
	    		}
	    		user.updateFile(PATH+ "/" + fkgroupnmbr + "_" + fksubject + ".html", user.getTeacherjournalContent(i.getNumber(), j.Name()));
	    	}
	    }
	}
}
