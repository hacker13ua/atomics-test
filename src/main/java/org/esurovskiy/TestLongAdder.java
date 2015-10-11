package org.esurovskiy;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * TODO: write JavaDoc
 *
 * @author Evgeniy Surovskiy
 */
public class TestLongAdder
{
    public static final int[] threadsCountArray = new int[]{1, 2, 4, 8, 16, 32, 48, 64};

    @State(Scope.Benchmark)
    public static class LongAdderWrapper
    {
        public LongAdder value = new LongAdder();
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public Long incrementAtomicLoop(final LongAdderWrapper longAdderWrapper)
    {
        for(int i = 0; i < 1_000_000; i++)
        {
            longAdderWrapper.value.increment();
        }
        return longAdderWrapper.value.longValue();
    }

    public static void main(String[] args) throws RunnerException
    {
        for(final int threadsCount : threadsCountArray)
        {
            Options opt = new OptionsBuilder()
                    .include(TestLongAdder.class.getSimpleName())
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
