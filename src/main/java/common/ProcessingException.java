package common;

public class ProcessingException extends RuntimeException {
    public ProcessingException(String productionId, String message) {
        super(formatMessage(productionId, message));
    }

    private static String formatMessage(String productionId, String message) {
        return String.format("[Processor: %s]: %s", productionId, message);
    }
}
