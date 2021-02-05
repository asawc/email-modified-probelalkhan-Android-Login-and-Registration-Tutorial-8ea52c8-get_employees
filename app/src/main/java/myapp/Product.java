package myapp;

public class Product {

    private int quantity;
    private String productname, productsymbol;

    public Product(int quantity, String productname, String productsymbol) {
        this.quantity = quantity;
        this.productname = productname;
        this.productsymbol = productsymbol;
    }

    public int getQuantity() { return quantity; }

    public String getProductname() { return productname; }

    public String getProductsymbol() { return productsymbol; }
}
