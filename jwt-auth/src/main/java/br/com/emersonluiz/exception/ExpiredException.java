package br.com.emersonluiz.exception;

public class ExpiredException extends Exception {

	private static final long serialVersionUID = 2029946097763067141L;

	public ExpiredException(String message) {
        super(message);
    }

}