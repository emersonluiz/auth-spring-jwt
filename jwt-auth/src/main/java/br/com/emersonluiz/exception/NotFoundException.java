package br.com.emersonluiz.exception;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = -8516400833389443445L;

	public NotFoundException(String message) {
        super(message);
    }
}
