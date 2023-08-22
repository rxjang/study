package chap08.item53;

public class VarargsExample {

    static int min(int ...args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("인수가 1개이상 필요합니다.");
        }
        int min = args[0];
        for (int i = 1; i < args.length; i++) {
            if (args[i] < min) min = args[i];
        }
        return min;
    }

    static int min(int firstArg, int ...remainingArgs) {
        int min = firstArg;
        for (int arg : remainingArgs) {
            if (arg < min) min = arg;
        }
        return min;
    }
}
