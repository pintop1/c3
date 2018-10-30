package charmingdev.d.c3;

/**
 * Created by LISA on 10/30/2018.
 */

public class ProductListCla {
    private int id;
    private String name;
    private String price;
    private String image;

    public ProductListCla(int id, String name, String price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
