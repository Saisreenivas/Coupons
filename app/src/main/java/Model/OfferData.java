package Model;

import android.graphics.Bitmap;

/**
 * Created by sai on 1/3/18.
 */

public class OfferData {
    private String merchant;
    private String category;
    private String basicDescription;
    private String cashBackPercentage;
    String activateUrl;
    String description;
    String noOfUses;
    String percentSuccess;
    int imgUrl;
    Bitmap img;

    public OfferData() {
    }

    public OfferData(String merchant, String category, String basicDescription, String cashBackPercentage) {
        this.merchant = merchant;
        this.category = category;
        this.basicDescription = basicDescription;
        this.cashBackPercentage = cashBackPercentage;
    }

    public OfferData(String merchant, String category, String basicDescription, String cashBackPercentage, Bitmap img) {
        this.merchant = merchant;
        this.category = category;
        this.basicDescription = basicDescription;
        this.cashBackPercentage = cashBackPercentage;
        this.img = img;
    }

    public String getActivateUrl() {
        return activateUrl;
    }

    public void setActivateUrl(String activateUrl) {
        this.activateUrl = activateUrl;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBasicDescription() {
        return basicDescription;
    }

    public void setBasicDescription(String basicDescription) {
        this.basicDescription = basicDescription;
    }

    public String getCashBackPercentage() {
        return cashBackPercentage;
    }

    public void setCashBackPercentage(String cashBackPercentage) {
        this.cashBackPercentage = cashBackPercentage;
    }
}
