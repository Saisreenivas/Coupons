package Model;

/**
 * Created by sai on 1/3/18.
 */

public class CategoryData {
    private String category;
    private String[] subCategories;
    private String[] subCategoryOffers;

    public CategoryData(String category, String[] subCategories, String[] subCategoryOffers) {
        this.category = category;
        this.subCategories = subCategories;
        this.subCategoryOffers = subCategoryOffers;
    }

    public CategoryData() {

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String[] getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(String[] subCategories) {
        this.subCategories = subCategories;
    }

    public String[] getSubCategoryOffers() {
        return subCategoryOffers;
    }

    public void setSubCategoryOffers(String[] subCategoryOffers) {
        this.subCategoryOffers = subCategoryOffers;
    }
}
