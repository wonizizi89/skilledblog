package com.example.myblog1.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.hibernate.loader.internal.AliasConstantsHelper.get;

@RestControllerAdvice //전역에 예외처리하는 어노테이션
public class ExceptionAdviceHandler {
    @ExceptionHandler({CustomException.class})
    protected ResponseEntity handleCustomException(CustomException ex) {
        return new ResponseEntity(new ErrorDto(ex.getExceptionStatus().getStatusCode(), ex.getExceptionStatus().getMessage()), HttpStatus.valueOf(ex.getExceptionStatus().getStatusCode()));
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        return new ResponseEntity<>(
                "["
                +e.getBindingResult().getFieldErrors().get(0).getField()
                +"] 은(이)"
                + e.getBindingResult().getFieldErrors().get(0).getDefaultMessage()
                +" / 입력 값 : "
                + e.getBindingResult().getFieldErrors().get(0).getRejectedValue(),
                HttpStatus.BAD_REQUEST);
    }
/* getBindingResult() 경우 똑같이 에러를 보여줌 , bad_request

.getFieldErrors() 경우
[
    {
        "codes": [
            "Size.signupRequest.username",
            "Size.username",
            "Size.java.lang.String",
            "Size"
        ],
        "arguments": [
            {
                "codes": [
                    "signupRequest.username",
                    "username"
                ],
                "arguments": null,
                "defaultMessage": "username",
                "code": "username"
            },
            10,
            4
        ],
        "defaultMessage": "size must be between 4 and 10",
        "objectName": "signupRequest",
        "field": "username",
        "rejectedValue": "won",
        "bindingFailure": false,
        "code": "Size"
    }
]

e.getBindingResult().getFieldErrors().get(0) 경우
{
    "codes": [
        "Size.signupRequest.username",
        "Size.username",
        "Size.java.lang.String",
        "Size"
    ],
    "arguments": [
        {
            "codes": [
                "signupRequest.username",
                "username"
            ],
            "arguments": null,
            "defaultMessage": "username",
            "code": "username"
        },
        10,
        4
    ],
    "defaultMessage": "size must be between 4 and 10",
    "objectName": "signupRequest",
    "field": "username",
    "rejectedValue": "won",
    "bindingFailure": false,
    "code": "Size"
}
*/
    @ExceptionHandler({RuntimeException.class})
    protected ResponseEntity<String> handlerEtcException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
