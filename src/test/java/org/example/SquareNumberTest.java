package org.example;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class SquareNumberTest {

    @Test
    @DisplayName("ThreadPool 미사용 테스트")
    public void withoutThreadPool() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int number = 1; number <= 10_000; number++) {
            Thread thread = new Thread(SquareNumber.create(number));
            threads.add(thread);
            thread.start();
        }

        // Join all threads to ensure completion
        for (Thread thread : threads) {
            thread.join();
        }
    }

    @Test
    @DisplayName("Fixed ThreadPool 사용 테스트")
    public void withThreadPool() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10); // Fixed thread pool with 10 threads

        for (int number = 1; number <= 10_000; number++) {
            executor.submit(SquareNumber.create(number));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES); // Wait for all tasks to finish
    }

    @Test
    @DisplayName("newSingleThreadExecutor 사용 테스트")
    public void withNewSingleThreadExecutor() throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        for (int number = 1; number <= 10_000; number++) {
            executor.submit(SquareNumber.create(number));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES); // Wait for all tasks to finish
    }

    @Test
    @DisplayName("Cached ThreadPool 사용 테스트")
    @Disabled
    public void withCachedThreadPool() throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int number = 1; number <= 10_000; number++) {
            executor.submit(SquareNumber.create(number));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES); // Wait for all tasks to finish
    }

    @Test
    @DisplayName("ThreadPoolExecutor 사용 테스트")
    public void withThreadPoolExecutor() throws InterruptedException {
        ThreadPoolExecutor executor= new ThreadPoolExecutor(2, 5, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000));
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        for (int number = 1; number <= 10_000; number++) {
            executor.submit(SquareNumber.create(number));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }
}
