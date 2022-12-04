package rummage.RummageMarket.Handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import rummage.RummageMarket.Handler.Ex.CustomApiException;
import rummage.RummageMarket.Handler.Ex.CustomException;
import rummage.RummageMarket.Handler.Ex.CustomValidationApiException;
import rummage.RummageMarket.Handler.Ex.CustomValidationException;
import rummage.RummageMarket.Util.Script;
import rummage.RummageMarket.Web.Dto.CMRespDto;

@RestController
@ControllerAdvice // 모든 exception을 다 낚아채는 어노테이션
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e) {
        if (e.getErrorMap() == null) {
            return Script.back(e.getMessage());
        } else {
            return Script.back(e.getErrorMap().toString());
        }
    }

    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public String exception(CustomException e) {
        return Script.back(e.getMessage());
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e) {
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

}