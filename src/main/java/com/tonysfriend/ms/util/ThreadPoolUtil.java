package com.tonysfriend.ms.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author tony.lu
 */
public class ThreadPoolUtil {
	private ExecutorService threadPool;
	private CompletionService<String> completionService;
	final BlockingQueue<Future<String>> queue = new LinkedBlockingDeque<Future<String>>(
			10);

	private ThreadPoolUtil() {
		int num = Runtime.getRuntime().availableProcessors();
		threadPool = Executors.newFixedThreadPool(num * 4);
		completionService = new ExecutorCompletionService<String>(threadPool,queue);

	}

	private static final ThreadPoolUtil manager = new ThreadPoolUtil();

	public static ThreadPoolUtil getInstance() {
		return manager;
	}

	public void addTask(Runnable runnable) {
		threadPool.execute(runnable);
	}

	public Future submit(Callable runnable) {
		return completionService.submit(runnable);
	}

	public Future<String> take() throws InterruptedException {
		return completionService.take();
	}
	
	
}
