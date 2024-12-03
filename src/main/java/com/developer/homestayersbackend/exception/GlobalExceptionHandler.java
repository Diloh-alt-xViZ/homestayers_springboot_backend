package com.developer.homestayersbackend.exception;

import com.developer.homestayersbackend.dto.RequestError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(VerificationTokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<RequestError> handleVerificationTokenExpiredException(VerificationTokenExpiredException e) {
        RequestError error = new RequestError();
        error.setMessage("Otp is expired");
        error.setStatus(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotVerifiedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RequestError> handleUserNotVerifiedException(UserNotVerifiedException e) {
        RequestError error = new RequestError();
        error.setMessage("User is not verified");
        error.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> handleIllegalArgumentExceptions(IllegalArgumentException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserProfileNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RequestError> handleUserProfileNotFoundException(UserProfileNotFoundException ex){
        RequestError error = new RequestError();
        error.setMessage("Profile not found");
        error.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VerificationTokenNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RequestError> handleVerificationTokenNotFoundException(VerificationTokenNotFoundException ex){
        RequestError error = new RequestError();
        error.setMessage("Invalid otp");
        error.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailVerificationTokenNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RequestError> handleEmailVerificationTokenNotFoundException(EmailVerificationTokenNotFoundException ex){
        RequestError error = new RequestError();
        error.setMessage("Email verification token not found");
        error.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(BookingNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RequestError> handleBookingNotFoundException(BookingNotFoundException ex) {
        RequestError error = new RequestError();
        error.setMessage(ex.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  ResponseEntity<RequestError> handleException(Exception ex){
        RequestError error = new RequestError();
        error.setMessage(ex.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PropertyAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RequestError> handlePropertyAlreadyExistsException(PropertyAlreadyExistsException ex){
        RequestError error = new RequestError();
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RequestError> handleRoomNotFoundException(RoomNotFoundException e) {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestError("Room not found",HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(RoomAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RequestError> handleRoomAlreadyExistsException(RoomAlreadyExistsException ex){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestError("Room already exists",HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(CancellationPolicyAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RequestError> handleCancellationPolicyAlreadyExistsException(CancellationPolicyAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestError("Cancellation Policy already defined",HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(CancellationPolicyNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RequestError> handleCancellationPolicyNotFoundException(CancellationPolicyNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestError("Cancellation Policy Not Found",HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(ServiceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RequestError> handleServiceAlreadyExistsException(ServiceAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestError("Service Already Exists",HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ServiceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RequestError> handleServiceNotFoundException(ServiceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestError("Service Not Found",HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(PropertyTypeExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RequestError> handlePropertyTypeExistsException(PropertyTypeExistsException ex) {

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestError("Property Type already exists", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(HostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RequestError> handleHostNotFoundException(HostNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestError("Host Not Found", HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(HostAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RequestError> handleHostAlreadyExistsException(HostAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestError("Host Already Exists", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(PropertyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RequestError> handlePropertyNotFoundException(PropertyNotFoundException ex) {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestError("PropertyType Not Found", HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(AmenityExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RequestError> handleAmenityExistsException(AmenityExistsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestError("Amenity already exists", HttpStatus.BAD_REQUEST));
    }



    @ExceptionHandler(AmenityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RequestError> handleAmenityNotFoundException(AmenityNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestError("Amenity not found", HttpStatus.NOT_FOUND));
    }
    @ExceptionHandler(AmenityCategoryExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RequestError> handleAmenityCategoryExistsException(AmenityCategoryExistsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestError("Amenity category exists", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(AmenityCategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RequestError> handleAmenityCategoryNotFoundException(AmenityCategoryNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestError("Amenity category not found", HttpStatus.NOT_FOUND));
    }
    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RequestError> handleUserExistsException(UserExistsException ex) {
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestError("User already exists", HttpStatus.BAD_REQUEST));
    }


    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RequestError> handleUserNotFoundException(UserNotFoundException ex) {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestError("User Not Found", HttpStatus.NOT_FOUND));
    }

}
