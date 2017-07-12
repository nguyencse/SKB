package vn.taa.mrta.object;

/**
 * Created by Putin on 4/16/2017.
 */

public class EventCustom {
    private String title;
    private long timeBegin;
    private long timeEnd;

    public EventCustom() {
    }

    public EventCustom(String title, long timeBegin, long timeEnd) {
        this.title = title;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(long timeBegin) {
        this.timeBegin = timeBegin;
    }

    public long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(long timeEnd) {
        this.timeEnd = timeEnd;
    }
}
