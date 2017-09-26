package xft.workbench.backstage.base.action;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.kayak.web.base.exception.KPromptException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {KPromptException.class})
    public ResponseEntity handlePrompt(KPromptException ex) {
        String bodyOfResponse = ex.getMessage();
        if (bodyOfResponse == null) {
            bodyOfResponse = "系统异常";
        }
        return Response.bad(bodyOfResponse);
    }


    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity handlePrompt() {
        return Response.bad("系统异常");
    }
}