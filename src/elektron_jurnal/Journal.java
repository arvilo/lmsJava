package elektron_jurnal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

class Journal {
	
	final private String db_ip;
	final private int db_port;
	final private String username;
	final private String db_pass;
	final String db_name;
	Connection connection;
	Statement statement;
	
	Journal(String ip, int port, String username, String pass, String name) {
		this.db_ip = ip;
		this.db_port = port;
		this.username = username;
		this.db_pass = pass;
		this.db_name = name;
		try {
			this.Connect();
			statement.execute("create database if not exists " + this.db_name);
			statement.execute("use " + this.db_name);
			statement.execute("create table if not exists teacher (id int primary key auto_increment,fin varchar(7) not null unique,first_name varchar(20) not null,last_name varchar(20) not null,birth date)");
			statement.execute("create table if not exists group_ (id int primary key auto_increment,number varchar(10) not null unique,specialty varchar(200))");
			statement.execute("create table if not exists student (id int primary key auto_increment,fin varchar(7) not null unique,first_name varchar(20) not null, last_name varchar(20) not null, birth date)");
			statement.execute("create table if not exists group__student (id int primary key auto_increment,group__id int,constraint fk2 foreign key (group__id) references group_(id), student_id int,constraint fk3 foreign key (student_id) references student(id))");
			statement.execute("create table if not exists subject (id int primary key auto_increment,name varchar(200),credit int not null,hours int not null, group__id int,constraint fk4 foreign key (group__id) references group_(id),teacher_id int,constraint fk5 foreign key (teacher_id) references teacher(id))");
			statement.execute("create table if not exists point (id int primary key auto_increment,name varchar(20) unique)");
			statement.execute("create table if not exists subject_point (id int primary key auto_increment,subject_id int not null,constraint fk9 foreign key (subject_id) references subject(id),point_id int,constraint fk10 foreign key (point_id) references point(id),max int not null,min int default 0)");
			statement.execute("create table if not exists result (id int primary key auto_increment,student_id int,constraint fk7 foreign key (student_id) references student(id),amount int default 0,subject_point_id int,constraint fk8 foreign key (subject_point_id) references subject_point(id), is_changed boolean default false, active boolean default true)");
			statement.execute("create table if not exists user (id int primary key auto_increment,username varchar(25) unique not null, pass varchar(20),type enum('admin','student','teacher'), owner_id int, reseted boolean default true)");
			ResultSet rslt = statement.executeQuery("select count(*) from " + this.db_name + ".point where name = 'exam'");
			rslt.next();
			if (rslt.getInt(1) == 0) {
				statement.execute("insert into point (name) values ('exam')");
			}
			
			rslt = statement.executeQuery("select count(*) from " + this.db_name + ".point where name = 'exam25%'");
			rslt.next();
			if (rslt.getInt(1) == 0) {
				statement.execute("insert into point (name) values ('exam25%')");
			}
			
			
			String query = "select count(*) from " + this.db_name + ".user where username = 'admin'";
			
			rslt = statement.executeQuery(query);
			rslt.next();
			if (rslt.getInt(1) == 0) {
				statement.execute("insert into user (username,pass,type) values ('admin','admin','admin')");
			}
		} catch (Exception e) {
			   e.printStackTrace();
		}
	}
	
	private void Connect() {
		 try {
	            Class.forName("com.mysql.cj.jdbc.Driver"); 
	            connection = DriverManager.getConnection("jdbc:mysql://"+this.db_ip+":"+this.db_port, this.username, this.db_pass);
	            statement = connection.createStatement();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	}
	
	void Close() {
		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void Drop() {
		try {
			statement.execute("drop database " + this.db_name);
			this.Close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	String Data(String table, int id, String column) {
		String query = "select " + column + " from " + this.db_name + "." + table + " where id = " + id;
		try {
			ResultSet result = this.statement.executeQuery(query);
			result.next();
			return result.getString(column);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	int[] TableIds (String table) {
		int[] rtr;
		ResultSet result;
		try{
			result = this.statement.executeQuery("select count(*) from " + this.db_name + "." + table);
			result.next();
			rtr = new int[result.getInt(1)];
			
			result = this.statement.executeQuery("select id from " + this.db_name + "." + table);
			for (int i = 0;i < rtr.length; i++) {
				result.next();
				rtr[i] = result.getInt("id");
			}
			return rtr;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override public boolean equals (Object k) {
		Journal o = (Journal)k;
		if (this.db_ip == o.db_ip && this.db_port == o.db_port && this.db_name == o.db_name) {
			return true;
		} else {
			return false;
		}
	}
}
