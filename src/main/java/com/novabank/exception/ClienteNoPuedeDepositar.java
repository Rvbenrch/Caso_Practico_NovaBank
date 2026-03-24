package main.java.com.novabank.exception;

public class ClienteNoPuedeDepositar extends RuntimeException {
    public ClienteNoPuedeDepositar(String message) {
        super(message);
    }
}
