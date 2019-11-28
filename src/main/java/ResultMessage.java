public class ResultMessage {

    private String URL;
    private Integer responseTime;
    private boolean success;

    public ResultMessage(String URL, Integer responseTime, boolean success) {
        this.URL = URL;
        this.responseTime = responseTime;
        this.success = success;
    }

    public String getURL() {
        return URL;
    }

    public Integer getResponseTime() {
        return responseTime;
    }

    public boolean isSuccess() {
        return success;
    }
}
