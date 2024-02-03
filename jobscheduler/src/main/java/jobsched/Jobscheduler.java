package jobsched;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import jobsched.AJob;

/**
 * The main class of the framework.
 * @author Emanuele Buonaccorsi
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


    /**
     * Emits a stream of jobs, acording to the strategy specified in the constructor.
     * @return a stream of jobs
     */
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
        return jobs
            .flatMap(job -> job.execute());
    }


    /**
     * Groups all the pairs with the same keys in a single pair, having the same key and the list of all values.
     * Attention: it removes duplicates.
     * @param myPairsStream
     * @return
     */
    public Stream<Pair<K, List<V>>> collect(Stream<Pair<K, V>> myPairsStream) {
        return myPairsStream
            .collect(Collectors
                    // Group the pairs by their keys
                    .groupingBy(
                            Pair::getKey,
                            Collectors.mapping(
                                    Pair::getValue, // Extract the value from each pair
                                    Collectors.toCollection(LinkedHashSet::new)))) // Collect the values into a DS that doesn't allow duplicates - so that the same word is not considered as an anagram of itself
            .entrySet() // returns a set of entries
            .stream()
            .map(e -> new Pair<>(e.getKey(), new ArrayList<>(e.getValue()))); // transforms each `Entry` into a `Pair`
    }

    
    /**
     * Outputs the results of the jobs, according to the strategy specified in the constructor
     * @param myStream a stream of {@link Pair}s containing the results of the jobs (computed after calling {@link #collect()})
     */
    public void output(Stream<Pair<K, List<V>>> myStream) {
        outputStrategy.output(myStream);
    }


    /**
     * Executes the whole pipeline implemented by the framework
     * i.e. calls, in sequence: {@link #emit()}, {@link #compute()}, {@link #collect()} and {@link #output()}
     */
    public void runPipeline(){
        Stream<AJob<K, V>> emitted = emit();
        Stream<Pair<K, V>> computed = compute(emitted);
        Stream<Pair<K, List<V>>> collected = collect(computed);
        output(collected);
    }
}
