package app.divyanshu.ecommerseappwithcart;

public class ProductsModel {

    public ProductsModel(String productName, String productAmount) {

        this.productName = productName;
        this.productAmount = productAmount;
    }

    public ProductsModel()
    {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
    String productName,productAmount;

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    Integer image;

    public ProductsModel(Integer image, String productName, String productAmount) {
        this.image = image;
        this.productName = productName;
        this.productAmount = productAmount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }
}
