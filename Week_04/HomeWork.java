import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class HomeWork {

    private static volatile int sum = 0;

    public static void test1() throws ExecutionException, InterruptedException {
        int result = 0;
        //使用FutureTask
        Callable<Integer> task = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sum();
            }
        };
        FutureTask<Integer> futureTask = new FutureTask<>(task);
        new Thread(futureTask).start();
        result = futureTask.get();
        System.out.println("异步计算结果为："+result);
    }

    public static void test2() throws InterruptedException {
        int result = 0;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(1);
        new Thread(() -> {
            int temp = sum();
            try {
                queue.put(temp);
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        countDownLatch.await();
        result = queue.poll();
        System.out.println("异步计算结果为："+result);
    }

    public static void test3() throws ExecutionException, InterruptedException {
        int result = 0;

        class Result {
            private int result;

            public int getResult() {
                return result;
            }

            public void setResult(int result) {
                this.result = result;
            }
        }

        class Task implements Runnable {
            private Result r;
            public Task(Result r) {
                this.r = r;
            }

            @Override
            public void run() {
                int result = sum();
                r.setResult(result);
            }
        }

        Result r = new Result();
        FutureTask<Result> futureTask1 = new FutureTask<>(new Task(r), r);
        new Thread(futureTask1).start();
        Result result1 = futureTask1.get();
        result = result1.getResult();
        System.out.println("异步计算结果为："+result);
    }

    public static void test4() throws InterruptedException {
        CountDownLatch countDownLatch1 = new CountDownLatch(1);
        class Task1 implements Runnable {
            private CountDownLatch countDownLatch;
            public Task1(CountDownLatch countDownLatch) {
                this.countDownLatch = countDownLatch;
            }
            @Override
            public void run() {
                int result = sum();
                sum = result;
                countDownLatch.countDown();
            }
        }

        new Thread(new Task1(countDownLatch1)).start();
        countDownLatch1.await();
        System.out.println("异步计算结果为："+ sum);
    }

    public static void test5() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(1);
        new Thread(() -> {
            sum = sum();
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("异步计算结果为："+ sum);
    }

    public static void test6() throws InterruptedException {
        //使用join
        Thread t = new Thread(() -> {
            sum = sum();
        });
        t.start();
        t.join();
        System.out.println("异步计算结果为："+ sum);
    }

    public static void test7() {
        Thread t1 = new Thread(() -> {
            sum = sum();
            LockSupport.park();
        });
        t1.start();
        LockSupport.unpark(t1);
        System.out.println("异步计算结果为："+ sum);
    }

    public static void test8() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> f = CompletableFuture.supplyAsync(() -> {
            return sum();
        });
        System.out.println("异步计算结果为："+ f.get());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start=System.currentTimeMillis();
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
        test7();
        test8();

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        // 然后退出main线程
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int n) {
        int constant = 1000000007;
        int first = 0;
        int second = 1;
        while (n-- > 0) {
            int temp = first + second;
            first = second % constant;
            second = temp % constant;
        }
        return first;
    }
}
