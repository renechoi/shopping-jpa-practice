package ssipgeukbbok.shoppingjpapractice.exception;

public class OutOfStockException extends RuntimeException{

//    private static final String message = "재고가 없습니다 !";

    public OutOfStockException(String message) {
        super(message);
        System.out.println("message = " + message);

    }
}
