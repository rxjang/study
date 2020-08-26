package sec04.exam01_field;

public class UsingThis {
	public int field=10;
	
	class Inner{
		int field=20;
		
		void method() {
			MyFunctionalInterface fi=()->{
				System.out.println("OutterField: "+UsingThis.this.field);
				System.out.println("innerField: "+field);
			};
			fi.method();
		}
	}
}
