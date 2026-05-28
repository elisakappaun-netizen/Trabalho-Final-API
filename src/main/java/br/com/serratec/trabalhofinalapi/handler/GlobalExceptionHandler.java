package br.com.serratec.trabalhofinalapi.handler;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegistroNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleRegistroNaoEncontrado(RegistroNaoEncontradoException ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ClienteException.class, VeiculoException.class, ServicoException.class, OrdemServicoException.class})
    public ResponseEntity<ErrorResponse> handleResourceNotFound(RuntimeException ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({DadosInvalidosException.class, CepInvalidoException.class, EnumInvalidoException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EnderecoException.class)
    public ResponseEntity<ErrorResponse> handleEnderecoError(EnderecoException ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(AutenticacaoException.class)
    public ResponseEntity<ErrorResponse> handleAutenticacao(AutenticacaoException ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler({DatabaseException.class, DataAccessException.class})
    public ResponseEntity<ErrorResponse> handleDatabaseError(RuntimeException ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class, MethodArgumentNotValidException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleValidationErrors(Exception ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnhandledException(Exception ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<ErrorResponse> buildResponse(Exception ex, HttpStatus status, WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");
        ErrorResponse body = new ErrorResponse(status.value(), status.getReasonPhrase(), ex.getMessage(), path);
        return new ResponseEntity<>(body, status);
    }
}
