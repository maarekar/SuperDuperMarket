package Exceptions;

public class MessageException extends Exception{
    private  String EXCEPTION_MESSAGE ;

    public MessageException(String message){
        this.EXCEPTION_MESSAGE=message;
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}
