package sec04.exam01_generic_method;

public class BoxingMethodExample {

	public static void main(String[] args) {
		Box<Integer> box1=Util.<Integer>boxing(100);	//메소드안에는 Integer값을 주어야 함
		int intValue=box1.get();
		System.out.println(intValue);
		
		Box<String> box2=Util.boxing("홍길동");
		String stringValue=box2.get();	//지네릭 지정 안하면 프로그램이 유추
		System.out.println(stringValue);
		
	}

}
