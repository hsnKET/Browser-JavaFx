package callbacks;

import javafx.scene.web.WebEngine;

public interface BrowserState {
    void onPageLoading(WebEngine webEngine);
    void onPageLoaded(WebEngine webEngine);
    void onBackForwardStateChange(boolean canForward,boolean canBack);
    void onSecureStateChange(boolean isHttps);
}
