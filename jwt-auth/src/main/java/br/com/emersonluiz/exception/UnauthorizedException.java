package br.com.emersonluiz.exception;

public class UnauthorizedException extends Exception {

	private static final long serialVersionUID = 2029946097763067141L;

	public UnauthorizedException(String message) {
        super(message);
    }

}