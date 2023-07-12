# item9. try-finally 보다는 try-with-resources를 사용하라.

자바 라이브러리에는 close()를 호출해 직접 닫아줘야 하는 자원이 많다. 그럴때, 확실히 닫아주기 위해 finally를 사용하는데 아래와 같이 잘못을 저지르는 경우가 발생한다.

``` java
static void copy(String src, String dst) throws IOException {
    InpuStream in = new FileInputStream(src);
    try {
        OutputStream out = new FileOutputStream(dst);
        try {
            byte[] buf = new byte[BUFFER_SIZE];
            int n;
            while ((n = in.read(buf)) >= 0)
                out.write(buf, 0, n);
        } finally {
            out.close();
        }
    } finally {
        in.close();
    }
}
```

이 문제는 자바 7에서부터 등장한 `try-with-resources`를 통해 해결할 수있다. `AutoCloseable` 인터페이스를 구핸해야 이를 사용할 수 있다. 
``` java
static void copy(String src, String dst) throws IOException {
    try(InpuStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst)) {
         byte[] buf = new byte[BUFFER_SIZE];
            int n;
            while ((n = in.read(buf)) >= 0)
                out.write(buf, 0, n);
    }    
}
```