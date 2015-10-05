package org.esurovskiy;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO: write JavaDoc
 *
 * @author Evgeniy Surovskiy
 */
@State(Scope.Benchmark)
public class TestAtomicInteger
{
    public static final int [] threadsCountArray = new int[]{1, 2, 4, 8, 16, 32, 48, 64};
    @State(Scope.Benchmark)
    public static class AtomicIntegerWrapper
    {
        public AtomicInteger value = new AtomicInteger(0);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public Integer incrementAtomicLoop(final AtomicIntegerWrapper atomicIntegerWrapper)
    {
        for(int i = 0; i < 1_000_000; i++)
        {
            atomicIntegerWrapper.value.incrementAndGet();
        }
        return atomicIntegerWrapper.value.get();
    }


    public static void main(String[] args) throws RunnerException
    {
        for(final int threadsCount : threadsCountArray)
        {
            Options opt = new OptionsBuilder()
                    .include(TestAtomicLong.class.getSimpleName())
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
