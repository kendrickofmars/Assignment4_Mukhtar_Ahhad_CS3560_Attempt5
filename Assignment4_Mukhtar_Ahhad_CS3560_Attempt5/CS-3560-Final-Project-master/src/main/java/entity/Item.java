package entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name="item")
public class Item {

    @Id
    @Column(name = "code")
    private String code;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "description")
    private String description;
    // Status: True if checkout-able False if currently checked out
    @Basic
    @Column(name = "status")
    private boolean status;
    @Basic
    @Column(name = "location")
    private String location;

    @Basic
    @Column(name ="daily_price")
    private double dailyPrice;

    @OneToOne(mappedBy = "itemByItemCode")
    private Book bookByCode;
    @OneToOne(mappedBy = "itemByItemCode")
    private Documentary documentaryByCode;



    public Item(String code, String title, String description, String location, double dailyPrice)
    {
        this.code = code;
        this.title = title;
        this.description = description;
        this.location = location;
        this.dailyPrice = dailyPrice;
        this.status = true;
    }

    public Item(){this.status=true;}
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return status == item.status && Objects.equals(code, item.code) && Objects.equals(title, item.title) && Objects.equals(description, item.description) && Objects.equals(location, item.location);
    }

    @Override
    public String toString()
    {
        return this.getTitle();
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, title, description, status, location);
    }

    public Book getBookByCode() {
        return bookByCode;
    }

    public void setBookByCode(Book bookByCode) {
        this.bookByCode = bookByCode;
    }

    public Documentary getDocumentaryByCode() {
        return documentaryByCode;
    }

    public void setDocumentaryByCode(Documentary documentaryByCode) {
        this.documentaryByCode = documentaryByCode;
    }

}
