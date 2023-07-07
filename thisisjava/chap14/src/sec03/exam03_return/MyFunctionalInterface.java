package sec03.exam03_return;

@FunctionalInterface	//함수적 인터페이스를 명확하게 작성하기 위해
public interface MyFunctionalInterface {
	public int method(int x, int y);	//메소드를 하나만 가지고 있다면 함수적 인터페이스-람다식으로 표현 가능
}
