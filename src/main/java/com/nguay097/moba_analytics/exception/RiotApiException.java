package com.nguay097.moba_analytics.exception;

/**
 * Exception thrown when a Riot Games API call fails.
 *
 * This exception encapsulates HTTP status codes and error messages from the Riot API,
 * allowing the application to propagate API errors to the caller with appropriate HTTP responses.
 */
public class RiotApiException extends RuntimeException {

    private final int statusCode;

    /**
     * Constructs a RiotApiException with a status code and error message.
     *
     * @param statusCode the HTTP status code from the Riot API (e.g., 404, 429, 503)
     * @param message a descriptive error message
     */
    public RiotApiException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    /**
     * Constructs a RiotApiException with a status code, error message, and root cause.
     *
     * @param statusCode the HTTP status code from the Riot API
     * @param message a descriptive error message
     * @param cause the underlying exception that caused this error
     */
    public RiotApiException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    /**
     * Gets the HTTP status code associated with this API error.
     *
     * @return the HTTP status code
     */
    public int getStatusCode() {
        return statusCode;
    }
}
