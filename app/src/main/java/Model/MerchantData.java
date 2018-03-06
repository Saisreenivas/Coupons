package Model;

/**
 * Created by sai on 1/3/18.
 */

public class MerchantData {
    private String merchant;
    private String offerPercentage;
    private String noOfOffers;

    public MerchantData() {
    }

    public MerchantData(String merchant, String offerPercentage, String noOfOffers) {
        this.merchant = merchant;
        this.offerPercentage = offerPercentage;
        this.noOfOffers = noOfOffers;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getOfferPercentage() {
        return offerPercentage;
    }

    public void setOfferPercentage(String offerPercentage) {
        this.offerPercentage = offerPercentage;
    }

    public String getNoOfOffers() {
        return noOfOffers;
    }

    public void setNoOfOffers(String noOfOffers) {
        this.noOfOffers = noOfOffers;
    }
}
