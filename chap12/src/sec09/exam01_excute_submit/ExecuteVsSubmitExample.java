package sec09.exam01_excute_submit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecuteVsSubmitExample {

	public static void main(String[] args) throws Exception {
		ExecutorService executorService=Executors.newFixedThreadPool(2);
		
		for (int i = 0; i < 10; i++) {
			Runnable runnable=new Runnable() {
				@Override
				public void run() {
					ThreadPoolExecutor threadPoolExecutor=(ThreadPoolExecutor)executorService;
					int poolSize=threadPoolExecutor.getPoolSize();
					String threadName=Thread.currentThread().getName();
					System.out.println("[총스레드 개수:"+poolSize+"] 작업 스레드 이름: "+threadName);
					int value=Integer.parseInt("삼");
				}
			};
//			executorService.execute(runnable);//예외 발생 
			executorService.submit(runnable);//예외 발생시키지 않고 실행 
			Thread.sleep(10);
		}
		
		
		executorService.shutdown();
	}

}
