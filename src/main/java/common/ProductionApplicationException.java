package common;

public class ProductionApplicationException extends RuntimeException {

    public ProductionApplicationException(int productionId, String message) {
        super(formatMessage(productionId, message));
    }

    private static String formatMessage(int productionId, String message) {
        return String.format("(P%d): %s", productionId, message);
    }
}
