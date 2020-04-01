package rusrobots.ru.test.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "SUPPLIER")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String supplierName;
    private String vendorLabel;
    private String numberLabel;
    private String descriptionLabel;
    private String priceLabel;
    private String countLabel;

    public Supplier() {
    }

    public Supplier(int id, String supplierName, String vendorLabel, String numberLabel, String descriptionLabel, String priceLabel, String countLabel) {
        this.id = id;
        this.supplierName = supplierName;
        this.vendorLabel = vendorLabel;
        this.numberLabel = numberLabel;
        this.descriptionLabel = descriptionLabel;
        this.priceLabel = priceLabel;
        this.countLabel = countLabel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return supplierName;
    }

    public String getVendorLabel() {
        return vendorLabel;
    }

    public void setVendorLabel(String vendorLabel) {
        this.vendorLabel = vendorLabel;
    }

    public String getNumberLabel() {
        return numberLabel;
    }

    public void setNumberLabel(String numberLabel) {
        this.numberLabel = numberLabel;
    }

    public String getDescriptionLabel() {
        return descriptionLabel;
    }

    public void setDescriptionLabel(String descriptionLabel) {
        this.descriptionLabel = descriptionLabel;
    }

    public String getPriceLabel() {
        return priceLabel;
    }

    public void setPriceLabel(String priceLabel) {
        this.priceLabel = priceLabel;
    }

    public String getCountLabel() {
        return countLabel;
    }

    public void setCountLabel(String countLabel) {
        this.countLabel = countLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Supplier)) return false;
        Supplier that = (Supplier) o;
        return supplierName.equals(that.supplierName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplierName);
    }

    @Override
    public String toString() {
        return "Конфигурация поставщика: " +
                "id=" + id +
                ", Наименование='" + supplierName + '\'' +
                ", Бренд='" + vendorLabel + '\'' +
                ", Каталожный номер='" + numberLabel + '\'' +
                ", Описание='" + descriptionLabel + '\'' +
                ", Цена='" + priceLabel + '\'' +
                ", Наличие='" + countLabel + '\'';
    }
}
