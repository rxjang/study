package chap07.item47;

import java.util.stream.Stream;

public class Item47Example {

    public static void main(String[] args) {
        for (ProcessHandle p : iterableOf(ProcessHandle.allProcesses())) {
            // 프로세스를 처리한다
        }
    }

    // Stream<E>를 Itreable<E>로 중개해주는 어댑터
    public static <E> Iterable<E> iterableOf(Stream<E> stream) {
        return stream::iterator;
    }
}
