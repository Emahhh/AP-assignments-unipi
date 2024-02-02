package jobsched;
import java.util.stream.Stream;

public interface EmitStrategy <K, V> {

    // doesn't take anything as an input
    public Stream<AJob<K,V>> emit();
}
