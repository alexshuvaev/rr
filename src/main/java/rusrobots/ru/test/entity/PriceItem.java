package rusrobots.ru.test.entity;

import javax.persistence.*;

@Entity
@Table(name = "PRICE_ITEM")
public class PriceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String vendor;
    private String number;
    private String searchVendor;
    private String searchNumber;
    private String description;
    private double price;
    private int count;

    public PriceItem() {
    }

    public PriceItem(String vendor, String number, String searchVendor, String searchNumber, String description, double price, int count) {
        this.vendor = vendor;
        this.number = number;
        this.searchVendor = searchVendor;
        this.searchNumber = searchNumber;
        this.description = description;
        this.price = price;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVendor() {
        return vendor;
    }

    public String getNumber() {
        return number;
    }

    public String getSearchVendor() {
        return searchVendor;
    }

    public String getSearchNumber() {
        return searchNumber;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PriceItem)) return false;
        PriceItem priceItem = (PriceItem) o;
        return id != null && id.equals(priceItem.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }

    @Override
    public String toString() {
        return "Строка в прайсе: " +
                "id=" + id +
                ", Бренд='" + vendor + '\'' +
                ", Каталожный номер='" + number + '\'' +
                ", Бренд (поиск)='" + searchVendor + '\'' +
                ", Каталожный номер (поиск)='" + searchNumber + '\'' +
                ", Описание='" + description + '\'' +
                ", Цена=" + price +
                ", Наличик=" + count;
    }
}
