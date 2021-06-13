package main.exception;

public class AppException extends Exception{
    public AppException(String message, Throwable cause){
        super(message,cause);
    }

}
