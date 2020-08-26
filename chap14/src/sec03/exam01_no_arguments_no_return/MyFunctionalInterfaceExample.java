package sec03.exam01_no_arguments_no_return;

public class MyFunctionalInterfaceExample {

	public static void main(String[] args) {
		MyFunctionalInterface fi;
		
		fi=() ->{
			String str="method call1";
			System.out.println(str);
		};
		fi.method();	//메소드 호출
		
		fi=()->{System.out.println("method call2");};
		fi.method();

		fi=()->System.out.println("method call3");	//중괄호 생략 가능 -실행문이 하나이기 때문에
		fi.method();
			
		fi=new MyFunctionalInterface() {//람다식 사용X
			public void method() {
				System.out.println("method call4");
			};
		};
		fi.method();
	}

}
