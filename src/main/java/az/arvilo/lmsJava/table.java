package az.arvilo.lmsJava;

public class table {
		String[][] datas;
		table (int y,int x) {
			this.datas = new String[y][x];
		}
		
		table (String[][] d)  {
			this.datas = d;
		}
		
		private int maxlngthclmn(int x) {
			int rtr = 0;
			for (String[] i: datas) {
				if (i[x] == null) {
					continue;
				}
				if (i[x].length() > rtr) {
					rtr = i[x].length();
				}
			}
			return rtr;
		}
		
		private int rowlngth () {
			int rtr = 1;
			for (int j = 0;j < datas[0].length;j++) {
				rtr += (this.maxlngthclmn(j) + 1);
			}
			return rtr;
		}
		
		private String border() {
			String rtr = "";
			for (int i = 0;i < this.rowlngth();i++) {
				rtr = rtr + "-";
			}
			return rtr;
		}
		
		String view() {
			String rtr = this.border();
			for (int i = 0;i < datas.length;i++) {
				rtr = rtr + "\n|";
				for (int j = 0;j < datas[0].length;j++) {
					if (datas[i][j] == null) {
						datas[i][j] = "-";
					}
					rtr = rtr + datas[i][j];
					for (int k = 0;k < (this.maxlngthclmn(j) - datas[i][j].length());k++) {
						rtr = rtr + " ";
					}
					rtr = rtr + "|";
				}
				rtr = rtr + "\n" + this.border();
			}
			return rtr;
		}
		
		String HTML () {
			String rtr = "<table>";
			
			rtr += "<thead>";
			rtr += "<tr>";
			for (int i = 0;i < this.datas[0].length;i++) {
				rtr += "<th>" + datas[0][i] + "</th>";
			}
			rtr += "</tr>";
			rtr += "</thead>";
			rtr +="<tbody>";
			for (int i = 1;i < this.datas.length;i++) {
				rtr += "<tr>";
				for (int j = 0;j < this.datas[i].length;j++) {
					rtr += "<td>" + datas[i][j] + "</td>";
				}
				rtr += "</tr>";
			}
			rtr +="</tbody>";
			rtr += "</table>";
			return rtr;
		}
		
	String HTMLwithateg() {
		String rtr = "<table>";
		
		rtr += "<thead>";
		rtr += "<tr>";
		for (int i = 0;i < this.datas[0].length;i++) {
			rtr += "<th>" + datas[0][i] + "</th>";
		}
		rtr += "</tr>";
		rtr += "</thead>";
		rtr +="<tbody>";
		for (int i = 1;i < this.datas.length;i++) {
			rtr += "<tr>";
			for (int j = 0;j < this.datas[i].length;j++) {
				rtr += "<td><a href = \"" + this.datas[i][3] + ".html\" class = \"ahr\">" + datas[i][j] + "</a></td>";
			}
			rtr += "</tr>";
		}
		rtr +="</tbody>";
		rtr += "</table>";
		return rtr;
	}
	
	String HTMLteachergroups () {
		String rtr = "<table>";
		
		rtr += "<thead>";
		rtr += "<tr>";
		for (int i = 0;i < this.datas[0].length;i++) {
			rtr += "<th>" + datas[0][i] + "</th>";
		}
		rtr += "</tr>";
		rtr += "</thead>";
		rtr +="<tbody>";
		for (int i = 1;i < this.datas.length;i++) {
			rtr += "<tr>";
			for (int j = 0;j < this.datas[i].length;j++) {
				String link = "";
				for (int k = 0;k < this.datas[i][1].length();k++) {
					if (this.datas[i][1].charAt(k) == '.' || this.datas[i][1].charAt(k) == '/' || this.datas[i][1].charAt(k) == ' ') {
						link += "_";
					}
					else {
						link += this.datas[i][1].charAt(k);
					}
				}
				
				rtr += "<td><a href = \"" + link + ".html\" class = \"ahr\">" + datas[i][j] + "</a></td>";
			}
			rtr += "</tr>";
		}
		rtr +="</tbody>";
		rtr += "</table>";
		return rtr;
	}
	
}
