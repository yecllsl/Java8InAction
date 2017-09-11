package lambdasinaction.chap7;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

import static lambdasinaction.chap7.ParallelStreamsHarness.FORK_JOIN_POOL;

public class ForkJoinSumCalculator extends RecursiveTask<Long> {//继承RecursiveTask来创建可以用于分支/合并的框架

    public static final long THRESHOLD = 10_000;//不再将任务分解位子任务的数组大小

    private final long[] numbers;//要求和的数组
    private final int start;//子任务处理数组的起始位置和
    private final int end;//结束位置

    public ForkJoinSumCalculator(long[] numbers) {//公共构造函数用于创建主任务
        this(numbers, 0, numbers.length);
    }

    private ForkJoinSumCalculator(long[] numbers, int start, int end) {//私有构造函数用于递归的方式为主任务创建子任务
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {//覆盖RecursiveTask抽象方法
        int length = end - start;//该任务负责求和部分的大小
        if (length <= THRESHOLD) {//如果小于阀值
            return computeSequentially();//顺序计算
        }
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length/2);//创建一个子任务为数组的前一半求和
        leftTask.fork();//利用ForkJoinPool另一个线程异步执行新建的子任务
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length/2, end);//创建一个子任务为数组的后一半求和
        Long rightResult = rightTask.compute();//同步执行第二个子任务，有可能允许进一步递归划分
        Long leftResult = leftTask.join();//都区第一个任务的结果，如果尚未完成就等待
        return leftResult + rightResult;//该任务的结果是两个子任务结果的组合
    }

    private long computeSequentially() {//在子任务不再可分时计算结果的简单算法
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return FORK_JOIN_POOL.invoke(task);
    }
}