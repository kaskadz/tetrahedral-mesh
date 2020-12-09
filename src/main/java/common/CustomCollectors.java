package common;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CustomCollectors {
    public static <T> Collector<T, ?, T> toSingle() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException("Single element expected");
                    }
                    return list.get(0);
                }
        );
    }
}
