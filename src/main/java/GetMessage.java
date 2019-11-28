public class GetMessage {

    private String URL;
    private Integer count;

    public GetMessage(String URL, Integer count) {
        this.URL = URL;
        this.count = count;
    }

    public String getURL() {
        return URL;
    }

    public Integer getCount() {
        return count;
    }
}
