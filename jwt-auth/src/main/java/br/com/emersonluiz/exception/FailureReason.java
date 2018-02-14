package br.com.emersonluiz.exception;

import java.io.Serializable;

public class FailureReason implements Serializable {

	private static final long serialVersionUID = 1181380611924201810L;

	private String url;
    private String failureReason;

    public FailureReason (String url, String failureReason) {
        this.url = url;
        this.failureReason = failureReason;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

}
