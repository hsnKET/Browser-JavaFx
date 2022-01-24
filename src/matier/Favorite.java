package matier;

import java.io.Serializable;
import java.util.Date;

public class Favorite implements Serializable {
    private String title;
    private String url;
    private Date date;

    public Favorite(String title, String url, Date date) {
        this.title = title;
        this.url = url;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
