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

    public Stream<AJob<K, V>> emit(EmitStrategy<K, V> emitStrategy) {
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


    public void output(Stream<Pair<K, List<V>>> myStream, OutputStrategy<K, V> outputStrategy) {
        outputStrategy.output(myStream);
    }


    /**
     * executes the whole pipeline implemented by the framework
     */
    public void computeAndPrint(){
        

    }
}
