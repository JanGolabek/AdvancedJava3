package no.kristiania;

public class Product extends Category {
    Long id;
    String product_name;
    String description;
    int price;
    int category_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + product_name + '\'' +
                ", description='" + description + '\'' +
                ", pris=" + price + '\'' +
                ", kateogir id=" + category_id + '\'' +
                '}';
    }
}
