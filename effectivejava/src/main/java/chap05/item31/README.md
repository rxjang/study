# Item 31. 한정적 와일드카드를 사용해 API의 유연성을 높이라.

## 한정적 와일드 카드 타입

- 불공변 방식보다 유연한 방식을 제공하기 위해 자바에서 제공하는 타입
- 유연성을 극대화기위해 원소의 생산자나 소비자용 입력 매개변수에 와일드카드 타입을 사용한다.

eg)

``` java
// ? extends 상위타입
public void pushAll(Iterable<? extends E> src) {
	for (E e : src) push(e);
}

// ? super 하위타입
public void popAll(Collection<? super E> dst) {
	while (!isEmpty()) dst.add(pop());
}
```

> **PECS**: producer-extends, consumer-super
>

→ 매개변수화 타입 T가 생산자라면 `<? extends T>` 를 사용하고, 소비자라면 `<? super T>` 를 사용하라.

## 타입 매개변수 VS 와일드카드

두 가지를 모두 사용할 수 있는 경우, 어떤 것을 써야 할까?

``` java
public static <E> void swap(List<E> list, int i, int j);

public static void swap(List<?> list, int i, int j);
```

- 메서드 선언에 타입 매개변수가 한 번만 나오면 와일드 카드로 대체하라.
- 이때 비한정적 타입 매개변수라면 비한정적 와일드 카드로 바꾸고, 한정적 타입 매개변수라면 한정적 와일드 카드로 바꾸면 된다.
- 그렇지 않은 경우는 타입 매개변수를 쓰면 된다.