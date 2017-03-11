package structure.heap.internal;

import java.time.LocalDateTime;

public class Contribution {
    private LocalDateTime time;
    private int fee;
    private String contributor;

    public Contribution(LocalDateTime time, int fee,  String contributor) {
        this.time = time;
        this.contributor = contributor;
        this.fee = fee;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public int getFee() {
        return fee;
    }

    public String getContributor() {
        return contributor;
    }
}
