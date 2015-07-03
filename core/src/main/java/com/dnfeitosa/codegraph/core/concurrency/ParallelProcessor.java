package com.dnfeitosa.codegraph.core.concurrency;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;


public class ParallelProcessor {

	private final int minThreads;
	private final int maxThreads;

	public ParallelProcessor(int minThreads, int maxThreads) {
		this.minThreads = minThreads;
		this.maxThreads = maxThreads;
	}

	public <I, O> void process(List<I> values, Executor<I, O> executor, ResultHandler<I, O> resultHandler) {
		ThreadPoolExecutor pool = getThreadPool();
		PoolMonitor monitor = new PoolMonitor(pool);
		Thread monitorThread = new Thread(monitor);
		monitorThread.start();

		for (final I value : values) {
			pool.execute(runnable(value, executor, resultHandler));
		}

		pool.shutdown();
		while (!pool.isTerminated()) {}
		monitor.shutdown();
	}

	public <I> void process(List<I> values, Executor<I, Void> executor) {
		ResultHandler<I, Void> voidHandler = voidHandler();
		process(values, executor, voidHandler);
	}

	private <I> ResultHandler<I, Void> voidHandler() {
		return new ResultHandler<I, Void>() {

			@Override
			public void handle(I input, Void value) {
			}
		};
	}

	private <I, O> Runnable runnable(final I input, final Executor<I, O> executor,
			final ResultHandler<I, O> resultHandler) {
		return new Runnable() {
			@Override
			public void run() {
				O result = executor.execute(input);
				resultHandler.handle(input, result);
			}
		};
	}

	private ThreadPoolExecutor getThreadPool() {
		return new ThreadPoolExecutor(minThreads, maxThreads, 5000, MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	}

	private static class PoolMonitor implements Runnable {

		private static final Logger LOGGER = Logger.getLogger(PoolMonitor.class);

		private final ThreadPoolExecutor pool;
		private boolean run = true;

		public PoolMonitor(ThreadPoolExecutor pool) {
			this.pool = pool;
		}

		public void shutdown() {
			this.run = false;
		}

		@Override
		public void run() {
			while (run) {
				LOGGER.info(format(
						"[%d/%d] Active: %d, Completed: %d, Tasks: %d",
						pool.getPoolSize(), pool.getCorePoolSize(), pool.getActiveCount(),
						pool.getCompletedTaskCount(), pool.getTaskCount()));
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					LOGGER.error(e);
				}
			}

		}
	}
}
