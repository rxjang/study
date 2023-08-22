# item54. null이 아닌, 빈 컬랙션이나 배열을 반환하라.

``` java
private final List<Cheese> cheesesInStock = ...;

/**
 * @return 매장 안의 모든 치즈 목록을 반환한다.
 *  단, 재고가 하나도 없다면 null을 반환한다. 
*/
public List<Cheese> getCheeses() {
    return cheeseInStock.isEmpty() ? null 
        : new ArrayList<>(cheesesInStock);
}
```
위 코드에서처럼 null을 반환한다면, 이 클라이언트는 굳이 이 null 상황을 처리하는 코드를 추가로 작성해야한다. 
클라이언트에서 방어 코드를 빼먹으면 오류가 발생할 수 있다.  

## null 반환이 좋지 않은 이유
빈 컨테이너를 할당하는데 비용이 든다는 이유로 null을 반환하는 쪽이 낫다는 주장이 있는데, 이는 틀린 주장이다.
1. 성능 분석 결과 이 할당이 성능 저하의 주범이라고 확인되지 않는한, 이 정도의 성능 차이는 신경 쓸 수준이 못된다. 
2. 빈 컬렉션과 배열은 굳이 새로 할당하지 않고도 반환 할 수 있다. 
``` java
// 빈 컬렉션을 반환하는 올바른 예
public List<Cheese> getCheeses() {
    return new ArrayList<>(cheesesInStock);
```
사용 패턴에 따라 빈 컬렉션 할당이 성능을 눈에 띄게 떨어뜨릴 수 있다. 이럴 땐 배먼 똑같은 빈 '불변' 컬렉션을 반환하면 된다. 

## 배열도 마찬가지이다.
배열을 쓸 때도 null을 반환하지 말고 길이가 0인 배열을 반환하라. 