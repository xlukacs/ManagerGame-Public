package core;

/**
 * When any error has occurred while serialising or writing to a file, this error will be thrown.
 */
public class FileErrorException extends java.lang.Exception{
    public FileErrorException(String errorMessage) {
        super(errorMessage);
    }

    public FileErrorException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
