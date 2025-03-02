package earlybird.earlybird.error;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;

import earlybird.earlybird.EarlybirdApplication;
import earlybird.earlybird.error.exception.BusinessBaseException;
import earlybird.earlybird.error.exception.NotFoundException;

import jakarta.validation.UnexpectedTypeException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.method.MethodValidationResult;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

@ExtendWith(OutputCaptureExtension.class)
class GlobalExceptionHandlerUnitTest {

    private final String requestURI = "/exception-uri";

    @DisplayName("Exception.class 핸들러 메시지 검증")
    @Test
    void handleExceptionMessage(CapturedOutput output) throws Exception {
        Exception exception = initException(Exception.class);
        MockHttpServletRequest request = initRequest();

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        globalExceptionHandler.handleException(exception, request);

        assertThat(output.getOut())
                .contains("Exception for " + requestURI + ": " + exception.getMessage());
        assertThat(output.getOut()).contains("ERROR");
    }

    @DisplayName("HttpRequestMethodNotSupportedException.class 핸들러 메시지 검증")
    @Test
    void handleHttpRequestMethodNotSupportedExceptionMessage(CapturedOutput output)
            throws Exception {
        HttpRequestMethodNotSupportedException exception =
                (HttpRequestMethodNotSupportedException)
                        initException(HttpRequestMethodNotSupportedException.class);
        MockHttpServletRequest request = initRequest();

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        globalExceptionHandler.handleHttpRequestMethodNotSupportedException(exception, request);

        assertThat(output.getOut())
                .contains(
                        "HttpRequestMethodNotSupportedException for "
                                + requestURI
                                + ": "
                                + exception.getMessage());
        assertThat(output.getOut()).contains("WARN");
    }

    @DisplayName("BusinessBaseException.class 핸들러 메시지 검증")
    @Test
    void handleBusinessBaseExceptionMessage(CapturedOutput output) throws Exception {
        BusinessBaseException exception =
                (BusinessBaseException) initException(BusinessBaseException.class);
        MockHttpServletRequest request = initRequest();

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        globalExceptionHandler.handleBusinessBaseException(exception, request);

        assertThat(output.getOut())
                .contains("BusinessException for " + requestURI + ": " + exception.getMessage());
        assertThat(output.getOut()).contains("WARN");
    }

    @DisplayName("NoResourceFoundException.class 핸들러 메시지 검증")
    @Test
    void handleNoResourceFoundExceptionMessage(CapturedOutput output) throws Exception {
        NoResourceFoundException exception =
                (NoResourceFoundException) initException(NoResourceFoundException.class);
        MockHttpServletRequest request = initRequest();

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        globalExceptionHandler.handleNoResourceFoundException(exception, request);

        assertThat(output.getOut())
                .contains(
                        "NoResourceFoundException for "
                                + requestURI
                                + ": "
                                + exception.getMessage());
        assertThat(output.getOut()).contains("WARN");
    }

    @DisplayName("HttpMessageNotReadableException.class 핸들러 메시지 검증")
    @Test
    void handleHttpMessageNotReadableExceptionMessage(CapturedOutput output) throws Exception {
        HttpMessageNotReadableException exception =
                (HttpMessageNotReadableException)
                        initException(HttpMessageNotReadableException.class);
        MockHttpServletRequest request = initRequest();

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        globalExceptionHandler.handleHttpMessageNotReadableException(exception, request);

        assertThat(output.getOut())
                .contains(
                        "HttpMessageNotReadableException for "
                                + requestURI
                                + ": "
                                + exception.getMessage());
        assertThat(output.getOut()).contains("WARN");
    }

    @DisplayName("MethodArgumentNotValidException.class 핸들러 메시지 검증")
    @Test
    void handleMethodArgumentNotValidExceptionMessage(CapturedOutput output) throws Exception {
        MethodArgumentNotValidException exception =
                (MethodArgumentNotValidException)
                        initException(MethodArgumentNotValidException.class);
        MockHttpServletRequest request = initRequest();

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        globalExceptionHandler.handleInvalidRequestArgumentException(exception, request);

        assertThat(output.getOut())
                .contains(
                        "MethodArgumentNotValidException for "
                                + requestURI
                                + ": "
                                + exception.getMessage());
        assertThat(output.getOut()).contains("WARN");
    }

    @DisplayName("IllegalArgumentException.class 핸들러 메시지 검증")
    @Test
    void handleIllegalArgumentExceptionMessage(CapturedOutput output) throws Exception {
        IllegalArgumentException exception =
                (IllegalArgumentException) initException(IllegalArgumentException.class);
        MockHttpServletRequest request = initRequest();

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        globalExceptionHandler.handleInvalidRequestArgumentException(exception, request);

        assertThat(output.getOut())
                .contains(
                        "IllegalArgumentException for "
                                + requestURI
                                + ": "
                                + exception.getMessage());
        assertThat(output.getOut()).contains("WARN");
    }

    @DisplayName("UnexpectedTypeException.class 핸들러 메시지 검증")
    @Test
    void handleUnexpectedTypeExceptionMessage(CapturedOutput output) throws Exception {
        UnexpectedTypeException exception =
                (UnexpectedTypeException) initException(UnexpectedTypeException.class);
        MockHttpServletRequest request = initRequest();

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        globalExceptionHandler.handleInvalidRequestArgumentException(exception, request);

        assertThat(output.getOut())
                .contains(
                        "UnexpectedTypeException for "
                                + requestURI
                                + ": "
                                + exception.getMessage());
        assertThat(output.getOut()).contains("WARN");
    }

    @DisplayName("HandlerMethodValidationException.class 핸들러 메시지 검증")
    @Test
    void handleHandlerMethodValidationExceptionMessage(CapturedOutput output) throws Exception {
        HandlerMethodValidationException exception =
                (HandlerMethodValidationException)
                        initException(HandlerMethodValidationException.class);
        MockHttpServletRequest request = initRequest();

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        globalExceptionHandler.handleInvalidRequestArgumentException(exception, request);

        assertThat(output.getOut())
                .contains(
                        new ObjectMapper()
                                .writeValueAsString(
                                        "HandlerMethodValidationException for "
                                                + requestURI
                                                + ": "
                                                + exception.getMessage()));
        assertThat(output.getOut()).contains("WARN");
    }

    @DisplayName("MethodArgumentTypeMismatchException.class 핸들러 메시지 검증")
    @Test
    void handleMethodArgumentTypeMismatchExceptionMessage(CapturedOutput output) throws Exception {
        MethodArgumentTypeMismatchException exception =
                (MethodArgumentTypeMismatchException)
                        initException(MethodArgumentTypeMismatchException.class);
        MockHttpServletRequest request = initRequest();

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        globalExceptionHandler.handleInvalidRequestArgumentException(exception, request);

        assertThat(output.getOut())
                .contains(
                        "MethodArgumentTypeMismatchException for "
                                + requestURI
                                + ": "
                                + exception.getMessage());
        assertThat(output.getOut()).contains("WARN");
    }

    @DisplayName("MissingRequestHeaderException.class 핸들러 메시지 검증")
    @Test
    void handleMissingRequestHeaderExceptionMessage(CapturedOutput output) throws Exception {
        MissingRequestHeaderException exception =
                (MissingRequestHeaderException) initException(MissingRequestHeaderException.class);
        MockHttpServletRequest request = initRequest();

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        globalExceptionHandler.handleInvalidRequestArgumentException(exception, request);

        assertThat(output.getOut())
                .contains(
                        "MissingRequestHeaderException for "
                                + requestURI
                                + ": "
                                + exception.getMessage());
        assertThat(output.getOut()).contains("WARN");
    }

    private Exception initException(Class<? extends Exception> exceptionClass) throws Exception {
        String exceptionMessage = "exception-message";

        Constructor<? extends Exception> constructor;
        Exception exception;

        if (exceptionClass.isAssignableFrom(BusinessBaseException.class)) {
            exception = new NotFoundException();
        } else if (exceptionClass.isAssignableFrom(NoResourceFoundException.class)) {
            exception = new NoResourceFoundException(HttpMethod.GET, "resource-path");
        } else if (exceptionClass.isAssignableFrom(MethodArgumentNotValidException.class)) {
            exception =
                    new MethodArgumentNotValidException(
                            new MethodParameter(
                                    EarlybirdApplication.class.getMethod("main", String[].class),
                                    0),
                            new BeanPropertyBindingResult(null, "param"));
        } else if (exceptionClass.isAssignableFrom(HandlerMethodValidationException.class)) {
            exception =
                    new HandlerMethodValidationException(
                            new MethodValidationResult() {
                                @Override
                                public Object getTarget() {
                                    return null;
                                }

                                @Override
                                public Method getMethod() {
                                    return null;
                                }

                                @Override
                                public boolean isForReturnValue() {
                                    return false;
                                }

                                @Override
                                public List<ParameterValidationResult> getAllValidationResults() {
                                    return List.of();
                                }
                            });
        } else if (exceptionClass.isAssignableFrom(MethodArgumentTypeMismatchException.class)) {
            exception =
                    new MethodArgumentTypeMismatchException(
                            new Object(), Long.class, null, null, new Exception());
        } else if (exceptionClass.isAssignableFrom(MissingRequestHeaderException.class)) {
            exception =
                    new MissingRequestHeaderException(
                            "headerName",
                            new MethodParameter(
                                    EarlybirdApplication.class.getMethod("main", String[].class),
                                    0));
        } else {
            constructor = exceptionClass.getConstructor(String.class);
            exception = constructor.newInstance(exceptionMessage);
        }

        return exception;
    }

    private MockHttpServletRequest initRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI(requestURI);
        return request;
    }
}
