package edu.project5;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import lombok.SneakyThrows;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(value = Scope.Thread)
public class ReflectionBenchmark {
    private Method method;
    private MethodHandle methodHandle;
    private Student student;
    private Function<Student, String> lambda;
    private static final String METHOD_NAME = "name";

    @SuppressWarnings("checkstyle:MagicNumber")
    @SneakyThrows
    public void run() {
        Options options = new OptionsBuilder()
            .include(ReflectionBenchmark.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .forks(1)
            .warmupForks(1)
            .warmupIterations(1)
            .warmupTime(TimeValue.seconds(5))
            .measurementIterations(1)
            .measurementTime(TimeValue.seconds(5))
            .resultFormat(ResultFormatType.CSV)
            .build();

        new Runner(options).run();
    }

    @SneakyThrows
    @Setup
    public void setup() {
        student = new Student("Ivan", "Zolo");

        method = Student.class.getDeclaredMethod(METHOD_NAME);
        method.setAccessible(true);


        methodHandle = MethodHandles.lookup().findGetter(Student.class, METHOD_NAME, String.class);

        CallSite site = LambdaMetafactory.metafactory(
            MethodHandles.lookup(),
            "apply",
            MethodType.methodType(Function.class),
            MethodType.methodType(Object.class, Object.class),
            MethodHandles.lookup().findVirtual(Student.class, METHOD_NAME, MethodType.methodType(String.class)),
            MethodType.methodType(String.class, Student.class)
        );
        lambda = (Function<Student, String>) site.getTarget().invokeExact();
    }

    @Benchmark
    public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    @SneakyThrows
    @Benchmark
    public void reflection(Blackhole bh) {
        String name = (String) method.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    @SneakyThrows
    public void methodHandleAccess(Blackhole bh) {
        String name = (String) methodHandle.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    @SneakyThrows
    public void lambdaMetaFactoryHandleAccess(Blackhole bh) {
        String name = lambda.apply(student);
        bh.consume(name);
    }

    private record Student(String name, String surname) {
    }
}
