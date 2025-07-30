package galena.nirvana;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Taken from <a href="https://stackoverflow.com/a/27872852/12469081">StackOverflow</a>
 */
public class DistinctBy {
    public static <T> Predicate<T> of(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}