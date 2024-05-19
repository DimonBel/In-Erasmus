package erasmus.networking.common.exceptions.errorDetails;

import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.time.LocalDateTime;


@Getter
@Setter
public class ErrorDetails {
    private String title;
    private int status;
    private String detail;
    private URI type;
    private URI instance;
    private LocalDateTime timestamp;
    private Error[] errors;

    public ErrorDetails(String title, int status, String detail, URI type, URI instance, Error[] errors) {
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.type = type;
        this.instance = instance;
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }
}
