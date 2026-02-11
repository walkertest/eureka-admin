package cn.springcloud.eureka.model;

/**
 *                "leaseInfo": {
 *                             "durationInSecs": 90,
 *                             "evictionTimestamp": 0,
 *                             "lastRenewalTimestamp": 1770780186142,
 *                             "registrationTimestamp": 1769586032598,
 *                             "renewalIntervalInSecs": 30,
 *                             "serviceUpTimestamp": 1769586032599
 *                         },
 */
public class LeaseInfo {
    private int durationInSecs;
    private long evictionTimestamp;
    private long lastRenewalTimestamp;
    private long registrationTimestamp;
    private int renewalIntervalInSecs;
    private long serviceUpTimestamp;

    public int getDurationInSecs() {
        return durationInSecs;
    }

    public void setDurationInSecs(int durationInSecs) {
        this.durationInSecs = durationInSecs;
    }

    public long getEvictionTimestamp() {
        return evictionTimestamp;
    }

    public void setEvictionTimestamp(long evictionTimestamp) {
        this.evictionTimestamp = evictionTimestamp;
    }

    public long getLastRenewalTimestamp() {
        return lastRenewalTimestamp;
    }

    public void setLastRenewalTimestamp(long lastRenewalTimestamp) {
        this.lastRenewalTimestamp = lastRenewalTimestamp;
    }

    public long getRegistrationTimestamp() {
        return registrationTimestamp;
    }

    public void setRegistrationTimestamp(long registrationTimestamp) {
        this.registrationTimestamp = registrationTimestamp;
    }

    public int getRenewalIntervalInSecs() {
        return renewalIntervalInSecs;
    }

    public void setRenewalIntervalInSecs(int renewalIntervalInSecs) {
        this.renewalIntervalInSecs = renewalIntervalInSecs;
    }

    public long getServiceUpTimestamp() {
        return serviceUpTimestamp;
    }

    public void setServiceUpTimestamp(long serviceUpTimestamp) {
        this.serviceUpTimestamp = serviceUpTimestamp;
    }

    @Override
    public String toString() {
        return "LeaseInfo{" +
                "durationInSecs=" + durationInSecs +
                ", evictionTimestamp=" + evictionTimestamp +
                ", lastRenewalTimestamp=" + lastRenewalTimestamp +
                ", registrationTimestamp=" + registrationTimestamp +
                ", renewalIntervalInSecs=" + renewalIntervalInSecs +
                ", serviceUpTimestamp=" + serviceUpTimestamp +
                '}';
    }
}
