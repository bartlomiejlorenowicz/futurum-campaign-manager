package pl.futurum.campaignmanager.exception;


public class InsufficientFundsException extends BusinessException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}