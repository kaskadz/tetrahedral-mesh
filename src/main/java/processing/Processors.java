package processing;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Processors {
    public static final Processor[] processors = {
            new Assignment1Processor(),
            new Assignment7Processor()
    };

    public static final Map<String, Processor> processorsMap = Arrays.stream(processors)
            .collect(Collectors.toMap(
                    Processor::getProcessorId,
                    processor -> processor));

    static {
        assert processors.length == processorsMap.keySet().size();
    }
}
