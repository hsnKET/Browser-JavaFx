package matier;


import java.io.Serializable;

public class UserAgent implements Serializable {
    private String title;
    private String agent;
    private boolean isChecked = false;

    public UserAgent(String title, String agent) {
        this.title = title;
        this.agent = agent;
    }

    public UserAgent(String title, String agent, boolean isChecked) {
        this.title = title;
        this.agent = agent;
        this.isChecked = isChecked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }
}
