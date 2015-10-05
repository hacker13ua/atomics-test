package org.esurovskiy;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO: write JavaDoc
 *
 * @author Evgeniy Surovskiy
 */
public class TestAtomicLong
{
    @State(Scope.Benchmark)
    public static class AtomicLongWrapper
    {
        public AtomicLong value = new AtomicLong(0l);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public Long incrementAtomicLoop(final AtomicLongWrapper atomicLongWrapper)
    {
        for(int i = 0; i < 1_000_000; i++)
        {
            atomicLongWrapper.value.incrementAndGet();
        }
        return atomicLongWrapper.value.get();
    }

    public static void main(String[] args) throws RunnerException
    {
        Options opt = new OptionsBuilder()
                .include(TestAtomicLong.class.getSimpleName())
                .jvmArgs("-server", "-Xmx2G")
                .warmupIterations(10)
                .measurementIterations(50)
                .forks(1)
                .threads(16)
                .build();
        new Runner(opt).run();
    }
}
