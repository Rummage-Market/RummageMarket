package rummage.RummageMarket.Handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import rummage.RummageMarket.Handler.Ex.CustomValidationException;
import rummage.RummageMarket.Web.Dto.CMRespDto;

@RestController
@ControllerAdvice // 모든 exception을 다 낚아채는 어노테이션
public class ControllerExceptionHandler {

	@ExceptionHandler(CustomValidationException.class)
	public CMRespDto<?> validationException(CustomValidationException e) {
		return new CMRespDto(-1, e.getMessage(), e.getErrorMap());
	}

}
