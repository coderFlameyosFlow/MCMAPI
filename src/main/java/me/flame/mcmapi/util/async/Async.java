package me.flame.mcmapi.util.async;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.flame.mcmapi.Minigame;

import java.time.Duration;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Utility class which makes running asynchronous tasks easier in a fluent syntax in a non-blocking manner.
 * <p>
 * This is known as a mega task; it runs multiple tasks in parallel from the same object allocated.
 * <p>
 * Here is normal CompletableFuture:
 * <pre>{@code
 *      var future = CompletableFuture.supplyAsync(() -> {
 *          Thread.sleep(5000);
 *          return 42;
 *      });
 *
 *      // no delay
 *      var future = CompletableFuture.supplyAsync(() -> 42);
 * }</pre>
 * <p>
 * Even with no delay it still kinda feels like boilerplate,
 * <p></p>
 * and for the delay, you have to wait for the thread to finish sleeping before you can get the result, and so that thread is going to be started, then delay, then get the result.
 * <p></p>
 * not fun, now here is the same exact thing but with Async:
 * <pre>{@code
 *      // they both use and return CompletableFuture
 *      var future = Async.of().delay(Duration.ofSeconds(5), () -> 42);
 *      // or TimeUnit alternative
 *      var future = Async.of().delay(5, TimeUnit.SECONDS, () -> 42));
 *
 *      // no delay
 *      var future = Async.of(() -> 42);
 * }</pre>
 * <p>
 * The oversimplified syntax is awesome in my opinion and makes the code nice and clean.
 * <p>
 * There are a lot of other great features, such as {@link #repeat(Duration, Duration, Consumer)}, here's an example usage:
 * <pre>{@code
 *      var task = Async.of();
 *      // repeats the task every 2 hours after a delay of 5 hours.
 *      task.repeat(Duration.ofHours(5), Duration.ofHours(2), () -> 42 * 6);
 *
 *      // you can run as many tasks as you want using "task"
 *      task.delay(Duration.ofHours(5), () -> 42);
 *      ...
 * }</pre>
 *
 * To cancel every task in the made "Async" object, you can use {@link Async#cancel()}. and an example:
 * <pre>{@code
 *      var task = ...;
 *      // cancels the mega task
 *      task.cancel();
 *
 *      }</pre>
 */
@Getter
public class Async<T> {
    private final ScheduledExecutorService executor;

    @Setter
    private boolean cancelled;

    private Async() {
        this.executor = Executors.newSingleThreadScheduledExecutor();
        this.cancelled = false;
    }

    public static <T> Async<T> of() {
        return new Async<>();
    }

    public static CompletableFuture<Void> of(Runnable task) {
        return CompletableFuture.runAsync(task);
    }

    public static <T> CompletableFuture<T> of(Callable<T> task) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return task.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @SneakyThrows
    public CompletableFuture<T> delay(Duration duration, Callable<T> runnable) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        var schedule = executor.schedule(() -> {
            try {
                completableFuture.complete(runnable.call());
            } catch (Exception t) {
                completableFuture.completeExceptionally(t);
            }
        }, duration.toMillis(), TimeUnit.MILLISECONDS);
        completableFuture.whenComplete((ignored, throwable) -> schedule.cancel(false));
        return completableFuture;
    }

    @SneakyThrows
    public void repeat(Duration delay, Duration period, Consumer<ScheduledExecutorService> runnable) {
        executor.scheduleAtFixedRate(() -> runnable.accept(executor), delay.toMillis(), period.toMillis(), TimeUnit.MILLISECONDS);
    }

    @SneakyThrows
    public void repeat(Duration period, Consumer<ScheduledExecutorService> runnable) {
        executor.scheduleAtFixedRate(() -> runnable.accept(executor), 0, period.toMillis(), TimeUnit.MILLISECONDS);
    }

    @SneakyThrows
    public void repeat(Duration delay, Duration period, Runnable runnable) {
        executor.scheduleAtFixedRate(runnable, delay.toMillis(), period.toMillis(), TimeUnit.MILLISECONDS);
    }

    @SneakyThrows
    public void repeat(Duration period, Runnable runnable) {
        executor.scheduleAtFixedRate(runnable, 0, period.toMillis(), TimeUnit.MILLISECONDS);
    }

    @SneakyThrows
    public void delay(long time, TimeUnit unit, Runnable runnable) {
        executor.schedule(runnable, time, unit);
    }

    public void run(Runnable runnable) {
        CompletableFuture.runAsync(runnable);
    }

    public CompletableFuture<T> supply(Supplier<T> runnable) {
        return CompletableFuture.supplyAsync(runnable);
    }

    public void cancel() {
        executor.shutdown();
    }

    public void cancelNow() {
        executor.shutdownNow();
    }
}
