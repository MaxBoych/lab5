public class PutMessage {

    private String URL;
    private Long responseTime;

    public PutMessage(String URL, Long responseTime) {
        this.URL = URL;
        this.responseTime = responseTime;
    }

    public String getURL() {
        return URL;
    }

    public Long getResponseTime() {
        return responseTime;
    }
}
