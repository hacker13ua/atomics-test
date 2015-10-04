package org.esurovskiy;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO: write JavaDoc
 *
 * @author Evgeniy Surovskiy
 */
@State(Scope.Benchmark)
public class TestAtomicLong
{
    AtomicLong value = new AtomicLong(0l);

   /* public void test()
    {*/
//        final TestAtomicLong testAtomicLong = new TestAtomicLong();

//        ForkJoinPool forkJoinPool = new ForkJoinPool();
//        System.out.println("Parallelism=" + forkJoinPool.getParallelism());
//        final List<ForkJoinTask<?>> tasks = new ArrayList<>();
//        for(int i = 0; i < forkJoinPool.getParallelism(); i++)
//        {
//            tasks.add(forkJoinPool.submit(new Callable<Long>()
//            {
//                @Override
//                public Long call() throws Exception
//                {
                    /*return*/ /*incrementLoop(value);*/
//        System.out.println("Value=" + value);
//                }
//            }));
//            System.out.println("Starting task №" + i);
//        }
       /* try
        {
            for(ForkJoinTask<?> task : tasks)
            {
                System.out.println("---value=" + task.get());

            }
            System.out.println("Value=" + value);
        }*/
       /* catch(Exception e)
        {
            e.printStackTrace();
        }*/
//    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public Long incrementAtomicLoop()
    {
//        long loopStartTime = System.currentTimeMillis();
//        long result = 0l;
        for(int i = 0; i < 1_000_000; i++)
        {
            /*result = */value.incrementAndGet();
        }
//        System.out.println(Thread.currentThread().getName() + ": time=" + (System.currentTimeMillis() - loopStartTime));
//        System.out.println(Thread.currentThread().getName() + ": value="+value.get());
        return value.get();
    }

    public static void main(String[] args) throws RunnerException
    {

        Options opt = new OptionsBuilder()
                .include(TestAtomicLong.class.getSimpleName())
                .jvmArgs("-server", "-Xmx2G")
                .warmupIterations(10)
                .measurementIterations(50)
                .forks(1)
                .threads(64)
                .build();
        new Runner(opt).run();

    }


}
