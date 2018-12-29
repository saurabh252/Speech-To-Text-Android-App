package saurabh.com.speechtotext.Service;

public interface ResponseCallback {
    void onSuccess(String json);
    void onError(String cause);
}
