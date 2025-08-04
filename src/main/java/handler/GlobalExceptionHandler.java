package handler;

import exception.QuoteNotFoundException;
import exception.SaveQuoteException;
import exception.UpdateQuoteException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(QuoteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleQuoteNotFound(HttpServletRequest request, QuoteNotFoundException exception) {
        log.error("QuoteNotFoundException occurred: URL={}", request.getRequestURL(), exception);

        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("errorMassage", exception.getMessage());
        modelAndView.addObject("url", request.getRequestURL());
        return modelAndView;
    }

    @ExceptionHandler({SaveQuoteException.class, UpdateQuoteException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleDatabaseExceptions(HttpServletRequest request, Exception exception) {
        log.error("Database exception occurred URL={}", request.getRequestURL(), exception);

        ModelAndView modelAndView = new ModelAndView("error/500");
        modelAndView.addObject("errorMassage", exception.getMessage());
        modelAndView.addObject("exception", exception);
        modelAndView.addObject("url", request.getRequestURL());
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handlerException(HttpServletRequest request, Exception exception) {
        log.error("Exception occurred URL={}", request.getRequestURL(), exception);
        ModelAndView modelAndView = new ModelAndView("error/500");
        modelAndView.addObject("errorMassage", "An unexpected error occurred.");
        modelAndView.addObject("exception", exception);
        modelAndView.addObject("url", request.getRequestURL());
        return modelAndView;
    }
}
