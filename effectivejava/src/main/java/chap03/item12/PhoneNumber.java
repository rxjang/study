package chap03.item12;

public class PhoneNumber {
    private final short areaCode, prefix, lineNum;

    public PhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode = rangeCheck(areaCode, 999, "지역코드");
        this.prefix = rangeCheck(prefix, 999, "프리픽스");
        this.lineNum = rangeCheck(lineNum, 999, "가입자 번호");
    }

    private static short rangeCheck(int val, int max, String arg) {
        if (val < 0 || val > max)
            throw new IllegalArgumentException(arg + ": " + val);
        return (short) val;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PhoneNumber))
            return false;
        PhoneNumber pn = (PhoneNumber) o;
        return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
    }

    @Override
    public int hashCode() {
        int result = Short.hashCode(areaCode);
        result = 31 * result + Short.hashCode(prefix);
        result = 31 * result + Short.hashCode(lineNum);
        return result;
    }

    /**
     * 전화번호의 문자열 표현을 반환한다.
     * 이 문자열은 XXX-YYYY-ZZZZ 형태의 11글자로 구성된다.
     * XXX는 지역코드, YYYY는 접두사, ZZZZ는 가입자 번호이다.
     * 각각의 대문자는 10진수 숫자 하나를 나타낸다.
     */
    @Override
    public String toString() {
        return String.format("%03d-%04d-%04d", areaCode, prefix, lineNum);
    }

}
