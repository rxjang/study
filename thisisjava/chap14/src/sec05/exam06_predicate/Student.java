package sec05.exam06_predicate;

public class Student {
	private String name;
	private String sex;
	private int score;
	
	public Student(String name, String sex, int score) {
		super();
		this.name = name;
		this.sex = sex;
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public String getSex() {
		return sex;
	}
	
	
}
