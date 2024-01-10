package elektron_jurnal;


public class Main {
	public static void main(String[] args) {
		Journal jr = new Journal("localhost",3306,"root","Aa123456","test_jr_v1");
		Session s = new Session();
		System.out.println("Succes");
	}
}
