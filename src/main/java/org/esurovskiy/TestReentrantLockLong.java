package org.esurovskiy;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO: write JavaDoc
 *
 * @author Evgeniy Surovskiy
 */
public class TestReentrantLockLong
{
    public static final int[] threadsCountArray = new int[]{1, 2, 4, 8, 16, 32, 48, 64};
    @State(Scope.Benchmark)
    public static class LongWrapper
    {
        ReentrantLock lock = new ReentrantLock(true);
        volatile Long value = 0l;
        Long incrementAndGet()
        {
            lock.lock();
            try
            {
                return ++value;
            }
            finally
            {
                lock.unlock();
            }
        }

        Long get()
        {
            return value;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public Long incrementAtomicLoop(final LongWrapper longWrapper)
    {
        for(int i = 0; i < 1_000_000; i++)
        {
            longWrapper.incrementAndGet();
        }
        System.out.println("Value=" + longWrapper.get());
        return longWrapper.get();
    }

    public static void main(String[] args) throws RunnerException
    {
        for(final int threadsCount : threadsCountArray)
        {
            Options opt = new OptionsBuilder()
                    .include(TestReentrantLockLong.class.getSimpleName())
                    .jvmArgs("-server", "-Xmx2G")
                    .warmupIterations(10)
                    .measurementIterations(25)
                    .forks(1)
                    .threads(threadsCount)
                    .build();
            new Runner(opt).run();
        }
    }
}
