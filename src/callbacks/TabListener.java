package callbacks;

public interface TabListener {

    void onBtnBackClicked();
    void onBtnForwardClicked();
    void onBtnReloadClicked();
    void onBtnStarClicked();
    void onEnterClicked(String query);
}
