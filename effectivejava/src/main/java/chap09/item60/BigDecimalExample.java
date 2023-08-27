package chap09.item60;

import java.math.BigDecimal;

public class BigDecimalExample {

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
}
