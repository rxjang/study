package sec06.exam01_generic_wildcard;

import java.util.Arrays;

public class WIldCardExample {
	public static void registerCourse(Course<?> course) {
		System.out.println(course.getName()+"수강생: "+Arrays.toString(course.getStudents()));
	}

	public static void registerCourseStudent(Course<? extends Student> course) {
		System.out.println(course.getName()+"수강생: "+Arrays.toString(course.getStudents()));
		//상위 클래스의 타입을 제한 Student,HighStudent만 가능
	}

	public static void registerCourseWorker(Course<? super Worker> course) {
		System.out.println(course.getName()+"수강생: "+Arrays.toString(course.getStudents()));
		//하위 클래스의 타입을 제한 Person,Worker만 가능
	}
	
	public static void main(String[] args) {
		Course<Person> personCourse=new Course<Person>("일반인과정",5);
		personCourse.add(new Person("일반인"));
		personCourse.add(new Person("직장인"));
		personCourse.add(new Person("학생"));
		personCourse.add(new Person("고등학생"));
		
		Course<Worker> workerCourse=new Course<>("직장인과정",5);
		workerCourse.add(new Worker("직장인"));
		Course<Student> studentCourse=new Course<>("학생과정",5);
		studentCourse.add(new Student("학생"));
		studentCourse.add(new HighStudent("고등학생"));

		Course<HighStudent> highStudentCourse=new Course<>("고등학생과정",5);
		highStudentCourse.add(new HighStudent("고등학생"));
		
		registerCourse(personCourse);
		registerCourse(workerCourse);
		registerCourse(studentCourse);
		registerCourse(highStudentCourse);
		System.out.println();

//		registerCourseStudent(personCourse);	//에러발생
//		registerCourseStudent(workerCourse);	//에러발생
		registerCourseStudent(studentCourse);
		registerCourseStudent(highStudentCourse);
		System.out.println();
		
		registerCourseWorker(personCourse);
		registerCourseWorker(workerCourse);
//		registerCourseWorker(studentCourse);	//에러발생
//		registerCourseWorker(highStudentCourse);	//에러발생
		System.out.println();
		

	}

}
