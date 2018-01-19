package test;

public class TestEntity<T> {
	private String name;
	private int age;
	
	private T data;
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public TestEntity() {
		super();
	}
	public TestEntity(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
