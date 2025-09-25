package oocl.ltravelbackend.common.handler;


import oocl.ltravelbackend.common.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidTravelPlanIdInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidTravelPlanIdInputException(Exception ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(InvalidTravelPlanPaginationInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidTravelPlanPaginationInput(Exception ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(InvalidTravelComponentIdInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidTravelComponentIdInputException(Exception ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(TravelPlanNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleTravelPlanNotFoundException(Exception ex) {
        return ex.getMessage();
    }
    @ExceptionHandler(InvalidCommentPaginationInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidCommentPaginationInputException(Exception ex) {
        return ex.getMessage();
    }
}
