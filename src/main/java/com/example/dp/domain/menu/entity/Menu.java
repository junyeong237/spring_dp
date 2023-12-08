package com.example.dp.domain.menu.entity;

import com.example.dp.domain.menucategory.entity.MenuCategory;
import com.example.dp.domain.menulike.entity.MenuLike;
import com.example.dp.domain.model.TimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_MENU")
public class Menu extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<MenuCategory> menuCategoryList = new ArrayList<>();

    @OneToMany(mappedBy = "menu", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<MenuLike> menuLikeList = new ArrayList<>();

    @Column(nullable = false)
    private Integer likeCounts;

    @Builder
    private Menu(String name, String description, Integer price, Integer quantity, Boolean status, Integer likeCounts) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.likeCounts = likeCounts;
    }

    public void update(final String name, final String description, final Integer price,
        final Integer quantity, final boolean status) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public void addMenuCategory(MenuCategory menuCategory) {
        this.menuCategoryList.add(menuCategory);
        menuCategory.setMenu(this);
    }
    public void subQuantity(Integer count){
        this.quantity -= count;
    }

    public void removeCategory(MenuCategory menuCategory) {
        this.menuCategoryList.remove(menuCategory);
        menuCategory.setMenu(null);
    }

    public void addLikeCounts(){
        this.likeCounts ++;
    }
    public void subLikeCounts(){
        this.likeCounts --;
    }


    public void addMenuLike(MenuLike menuLike){
        this.menuLikeList.add(menuLike);
        this.addLikeCounts();
        menuLike.setMenu(this);
    }

}
