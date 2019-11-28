public class PutMessage {

    private String URL;
    private Integer responseTime;

    public PutMessage(String URL, Integer responseTime) {
        this.URL = URL;
        this.responseTime = responseTime;
    }

    public String getURL() {
        return URL;
    }

    public Integer getResponseTime() {
        return responseTime;
    }
}
