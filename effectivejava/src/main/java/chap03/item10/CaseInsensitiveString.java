package chap03.item10;

public class CaseInsensitiveString {
    private final String s;

    public CaseInsensitiveString(String s) {
        this.s = s;
    }

    @Override
    public boolean equals(Object obj) {
        /*
        if (obj instanceof CaseInsensitiveString) {
            return s.equalsIgnoreCase(((CaseInsensitiveString) obj).s);
        }

        if (obj instanceof String) {
            return s.equalsIgnoreCase((String) obj);
        }
        return false;
        */

        // String과도 연동하겠다는 생각 버리기
        return obj instanceof CaseInsensitiveString && ((CaseInsensitiveString) obj).s.equalsIgnoreCase(s);
    }
}
