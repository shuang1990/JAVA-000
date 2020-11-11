import java.util.concurrent.*;

public class HomeWork {

    private static volatile int sum = 0;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start=System.currentTimeMillis();
        // 返回结果初始化
        int result = 0;
        //------------------- 方式一开始--------------------
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
        //------------------- 方式一结束--------------------

        //------------------- 方式二开始--------------------
        //使用阻塞队列
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
        //------------------- 方式二结束--------------------

        //------------------- 方式三开始--------------------
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
        //------------------- 方式三结束--------------------

        //------------------- 方式四开始--------------------
        //操作共享变量
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
        result = sum;
        //------------------- 方式四结束--------------------

        //------------------- 方式五开始--------------------
        //操作共享变量
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
        result = sum();
        //------------------- 方式五结束--------------------

        //------------------- 方式六开始--------------------
        //使用join
        Thread t = new Thread(() -> {
            sum = sum();
        });
        t.join();
        result = sum();
        //------------------- 方式六结束--------------------

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);
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
