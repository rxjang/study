# item72. 표준 예외를 사용하라
예외 또한 재사용하는 것이 좋다.
## 표준 예외 사용시의 이점
1. 우리의 API가 다른 사람이 익히고 사용하기 쉬워진다.
2. 우리의 API를 사용한 프로그램도 낯선 예외를 사용하지 않게 되어 읽기 쉽게 된다.
3. 예외 클래스 수가 적을수록 메모리 사용량도 줄고 클래스를 적제하는 시간도 적게 걸린다. 

## 자주 사용하는 예외들
| 예외                              | 주요쓰임                          |
|---------------------------------|-------------------------------|
| IllegalArgumentException        | 허용하지 않는 값이 인수로 건네졌을 때         |
| IllegalStateException           | 객체가 메서드를 수행하기에 적절하지 않은 상태일 때  |
| NullPointerException            | null을 허용하지 않는 메서드에 nul을 건넸을 때 |
| IndexOutOfBoundsException       | 인덱스가 범위를 넘어섰을 때               |
| ConcurrentModificationException | 허용하지 않는 동시 수정이 발견됐을 때         |
| UnsupportedOperationException   | 호출한 메서드를 지원하지 않을 때            |


