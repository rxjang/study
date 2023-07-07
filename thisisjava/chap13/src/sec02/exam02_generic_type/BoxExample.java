package sec02.exam02_generic_type;

public class BoxExample {

	public static void main(String[] args) {
		Box<String> box=new Box<String>();
		
		box.set("hello");
		String greet=box.get();
		
		Box<Integer> box2=new Box<Integer>();
		box2.set(6);
		int value=box2.get();
		
	}

}
