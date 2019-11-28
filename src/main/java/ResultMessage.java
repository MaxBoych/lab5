public class ResultMessage {

    private String URL;
    private Long responseTime;
    private boolean success;

    public ResultMessage(String URL, Long responseTime, boolean success) {
        this.URL = URL;
        this.responseTime = responseTime;
        this.success = success;
    }

    public String getURL() {
        return URL;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public boolean isSuccess() {
        return success;
    }
}
