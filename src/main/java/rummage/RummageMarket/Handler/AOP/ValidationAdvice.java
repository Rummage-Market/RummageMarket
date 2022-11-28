package rummage.RummageMarket.Handler.AOP;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import rummage.RummageMarket.Handler.Ex.CustomValidationApiException;
import rummage.RummageMarket.Handler.Ex.CustomValidationException;

@Component 
@Aspect
public class ValidationAdvice {
    @Around("execution(* rummage.RummageMarket.Web.Api.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

//        System.out.println("web api 컨트롤러 =====================");
        Object[] args = proceedingJoinPoint.getArgs ();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
                }

            }
        }
        // proceedingJoinPoint => profile 함수의 모든 곳에 접근할 수 있는 변수
        // profile 함수보다 먼저 실행

        return proceedingJoinPoint.proceed(); // profile 함수가 실행됨.
    }

    @Around("execution(* rummage.RummageMarket.Web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

//        System.out.println("web 컨트롤러 ==========================");
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;
                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성 검사 실패함", errorMap);
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }
}