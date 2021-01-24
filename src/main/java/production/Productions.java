package production;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Productions {
    public static final Production[] productions = {
            new Production1(),
            new Production2(),
            new Production3(),
            new Production4(),
            new Production5(),
            new Production6(),
            new Production7(),
            new Production8(),
            new Production9(),
            new Production12(),
            new Production13()
    };

    public static final Map<Integer, Production> productionsMap = Arrays.stream(productions)
            .collect(Collectors.toMap(
                    Production::getProductionId,
                    production -> production));

    static {
        assert productions.length == productionsMap.keySet().size();
    }
}
