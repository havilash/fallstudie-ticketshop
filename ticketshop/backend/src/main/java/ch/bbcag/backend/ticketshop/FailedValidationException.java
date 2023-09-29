package ch.bbcag.backend.ticketshop;

import java.util.List;
import java.util.Map;

public class FailedValidationException extends RuntimeException{
    Map<String, List<String>> errors;

    public FailedValidationException(Map<String, List<String>> errors) {
        super(errors.toString());
        this.errors = errors;
    }
}
