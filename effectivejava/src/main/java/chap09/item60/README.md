# item60. 정확한 답이 필요하다면 float와 double은 피하라
## float과 double은 과학과 공학계산용으로 설계되었다
* 이들은 이진 부동소수점 연산에 쓰이며, 넓은 범위의 수를 빠르게 정밀한 근사치로 계산하도록 세심하계 설계됨
* 그러므로 정확한 결과가 필요할 때는 사용하면 안됨
* 특히 금융관련 계산과는 맞지 않음

``` java
 public static void main(String[] args) {
    double funds = 1.00;
    int itemBought = 0;
    for (double price = 0.10; funds >= price; price += 0.10) {
        funds -= price;
        itemBought++;
    }

    System.out.println(itemBought + "개 구입");
    System.out.println("잔돈(달러)" + funds);
}
```
위 예시에서 잔돈은 0.3999999999999999달러다. 이는 잘못된 결과다. 

## 금융 계산에는 BigDecimal, int 혹은 long을 사용하자.
``` java
public static void main(String[] args) {
    final BigDecimal TEN_CENTS = new BigDecimal(".10");

    int itemBought = 0;
    BigDecimal funds = new BigDecimal("1.00");
    for (BigDecimal price = TEN_CENTS; funds.compareTo(price) >= 0; price = price.add(TEN_CENTS)) {
        funds = funds.subtract(price);
        itemBought++;
    }

    System.out.println(itemBought + "개 구입");
    System.out.println("잔돈(달러)" + funds);
}
```
위 예시는 float을 사용했던 것을 BigDecimal로 바꾼것이다. 결과는 정상적으로 잔돈 0개가 나온다.

## BigDecimal의 단점
BigDecimal은 기본타입보다 쓰기 불편하고, 느리다. 이 대안으로 int 혹은 long타입을 쓸 수 있다. 대신 그럴 경우 사용할 수 있는 값의 크기가 제한되고, 소수점을 직접 관리해야한다. 
