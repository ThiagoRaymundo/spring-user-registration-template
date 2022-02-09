package br.com.userregistration.common.validations;

import javax.validation.ConstraintViolationException;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.userregistration.common.validations.dto.StandardErrorResponse;
import br.com.userregistration.common.validations.dto.ValidationErrorResponse;
import br.com.userregistration.common.validations.exceptions.BusinessException;
import br.com.userregistration.common.validations.exceptions.EmailException;

@RestControllerAdvice
public class ValidationsExcetionsHandler {

	@Autowired
	private MessageSource messageSource;

	private StandardErrorResponse error;

	public ValidationsExcetionsHandler() {
		this.error = new StandardErrorResponse();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardErrorResponse> handlerFieldValidation(MethodArgumentNotValidException exception) {
		ValidationErrorResponse validationError = 
				new ValidationErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation Error");
		exception.getBindingResult().getFieldErrors().forEach(e -> {
			String msg = this.messageSource
					      .getMessage(e, 
					    		  LocaleContextHolder.getLocale());
			validationError.addError(e.getField(), msg);
		});
		return ResponseEntity.badRequest().body(validationError);
	}

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardErrorResponse> handlerObjectNotFound(ObjectNotFoundException exception) {
		this.error.addStandrError(HttpStatus
				         .NOT_FOUND
				         .value(), 
				         exception.getMessage());
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(error);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<StandardErrorResponse> handlerrequestParamIsNotPresent(
			MissingServletRequestParameterException exception) {
		this.error.addStandrError(
				HttpStatus
				.BAD_REQUEST
				.value(), 
				exception.getMessage());
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StandardErrorResponse> handlerConstraintViolationException(
			ConstraintViolationException exception) {
		this.error.addStandrError(HttpStatus
				.BAD_REQUEST
				.value(), 
				exception.getMessage());
		return ResponseEntity
				.status(HttpStatus
						.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<StandardErrorResponse> handlerBusinessException(BusinessException ex) {
		this.error.addStandrError(HttpStatus
				.UNPROCESSABLE_ENTITY
				.value(), 
				ex.getMessage());
		return ResponseEntity
				.status(HttpStatus
						.UNPROCESSABLE_ENTITY).body(error);
	}
	
	@ExceptionHandler(EmailException.class)
	public ResponseEntity<StandardErrorResponse> handlerEmailException(EmailException ex) {
		this.error.addStandrError(HttpStatus
				.INTERNAL_SERVER_ERROR
				.value(), 
				ex.getMessage());
		return ResponseEntity
				.status(HttpStatus
						.INTERNAL_SERVER_ERROR).body(error);

	}
}


