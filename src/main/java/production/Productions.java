package production;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Productions {
    public static final Production[] productions = {
            new Production1(),
            new Production2()
    };

    public static final Map<Integer, Production> productionsMap = Arrays.stream(productions)
            .collect(Collectors.toMap(
                    Production::getProductionId,
                    production -> production));

    static {
        assert productions.length == productionsMap.keySet().size();
    }
}
