package entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Director {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "nationality")
    private String nationality;
    @Basic
    @Column(name = "style")
    private String style;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Director director = (Director) o;
        return Objects.equals(name, director.name) && Objects.equals(nationality, director.nationality) && Objects.equals(style, director.style);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nationality, style);
    }
}
