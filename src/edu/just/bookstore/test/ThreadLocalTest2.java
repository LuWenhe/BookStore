package edu.just.bookstore.test;

public class ThreadLocalTest2 implements Runnable {

	ThreadLocal<String> threadLocal = new ThreadLocal<>();
	int i = 0;
	
	@Override
	public void run() {
		for(; i<10; i++) {
			synchronized (this) {
				threadLocal.set(Thread.currentThread().getName());
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {}
				
				System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());
			}
		}
	}

	public static void main(String[] args) {
		ThreadLocalTest2 tt = new ThreadLocalTest2();
		new Thread(tt, "AAA").start();
		new Thread(tt, "BBB").start();
		new Thread(tt, "CCC").start();
	}
}
