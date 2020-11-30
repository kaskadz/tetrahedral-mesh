package production;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Production1Test {
    private final Production sut = new Production1();

    @Test
    public void ShouldHaveProperNumber() {
        Production sut = new Production1();
        assertEquals(1, sut.getProductionId());
    }
}
