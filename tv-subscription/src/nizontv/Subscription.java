package nizontv;

import java.io.Serializable;

public class Subscription implements Serializable {
    private int installFee;
    private int numberTv;
    private Subscriber subscriber;
    private SubscriptionCycle subCycle;
    private String datee;
    private int totalFee;

    public Subscription(int numberTv, Subscriber subscriber, SubscriptionCycle subCycle, String datee) {
        this.numberTv = numberTv;
        this.subscriber = subscriber;
        this.subCycle = subCycle;
        this.datee = datee;

        this.installFee = numberTv*10;
    }

    public int getNumberTv() {
        return numberTv;
    }

    public void setNumberTv(int numberTv) {
        this.numberTv = numberTv;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public SubscriptionCycle getSubCycle() {
        return subCycle;
    }

    public void setSubCycle(SubscriptionCycle subCycle) {
        this.subCycle = subCycle;
    }

    public String getDatee() {
        return datee;
    }

    public void setDatee(String datee) {
        this.datee = datee;
    }

    public int getTotalFee() {
        totalFee= installFee+5;
        return totalFee;
    }


    @Override
    public String toString() {
        return "Subscription{" +
                "installFee=" + installFee +
                ", numberTv=" + numberTv +
                ", subscriber=" + subscriber +
                ", subCycle=" + subCycle +
                ", today='" + datee + '\'' +
                ", totalFee=" + totalFee +
                '}';
    }
}

