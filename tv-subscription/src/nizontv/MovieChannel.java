package nizontv;

public class MovieChannel extends TvChannel{
    int additionalFee = 15;
    public MovieChannel(String channelName, String language, String category, int price) {
        super(channelName, language, category, price);
    }

    @Override
    public int getPrice() {
        return super.getPrice()+additionalFee;
    }
}
