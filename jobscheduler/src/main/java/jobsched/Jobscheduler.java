package jobsched;

import java.util.List;
import java.util.stream.Stream;
import jobsched.AJob;

/**
 *
 * It is the context.
 * @author ema
 */
public class Jobscheduler<K, V> {

    private final EmitStrategy<K, V> emitStrategy;
    private final OutputStrategy<K, V> outputStrategy;

    /**
     * Constructor that allows to specify the strategies for both emit and output.
     * @param emitStrategy the strategy for emitting a stream of jobs
     * @param outputStrategy the strategy for printing the results
     */
    public Jobscheduler(EmitStrategy<K, V> emitStrategy, OutputStrategy<K, V> outputStrategy) {
        this.emitStrategy = emitStrategy;
        this.outputStrategy = outputStrategy;
    }

    public Stream<AJob<K, V>> emit() {
        return emitStrategy.emit();
    }

    /**
     * Computes all jobs by calling {@link AJob#execute()} and concatenating the results.
     * This is a template method (frozen spot), in fact it calls {@link AJob#execute()} (a hook).
     * 
     * @return a stream of {@link Pair}s, containing the results of each job
     */
    public Stream<Pair<K, V>> compute(Stream<AJob<K, V>> jobs) {
        return jobs.flatMap(job -> job.execute());
    }


    public void output(Stream<Pair<K, List<V>>> myStream) {
        outputStrategy.output(myStream);
    }


    /**
     * executes the whole pipeline implemented by the framework
     */
    public void computeAndPrint(){


    }
}
