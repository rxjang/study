# item43. 람다보다는 메서드 참조를 사용하라
람다가 익명 클래스보다 나은 가장 큰 장점은 **간결함**이다. 그런데 메소드 참조(method reference)가 람다보다 더 간결할 때도 있다. 

### 메소드 참조 유형
| 메서즈 참조유형   | 예                      | 같은 기능을 하는 람다                                          |
|------------|------------------------|-------------------------------------------------------|
| 정적         | Integer::parseInt      | str -> Integer.parseInt(str)                          |
| 한정적(인스턴스)  | Instant.now()::isAfter | Instant then = Instant.now()<br/>t -> then.isAfter(t) |
| 비한정적(인스턴스) | String::toLowerCase    | str -> str.toLowerCase()                              |
| 클래스 생성자    | TreeMap<K, V>::new     | () -> new TreeMap<K,V>()                              |
| 배열 생성자     | int[]::new             | len -> new int[len]                                   |

-> 메서드 참조 쪽이 짧고 명확하다면 메서드 참조를 쓰고, 그렇지 않을 때만 람다를 사용하라.