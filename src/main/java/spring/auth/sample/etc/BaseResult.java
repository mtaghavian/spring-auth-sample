package spring.auth.sample.etc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;


/**
 * @author Masoud Taghavian (masoud.taghavian@gmail.com)
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseResult<T> {

    private T content;

    private HttpStatus status;

    private String errorMessage;
}
