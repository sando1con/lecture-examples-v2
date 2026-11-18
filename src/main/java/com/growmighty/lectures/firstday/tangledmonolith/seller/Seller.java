package com.growmighty.lectures.firstday.tangledmonolith.seller;

import com.growmighty.lectures.firstday.tangledmonolith.product.Product;
import com.growmighty.lectures.firstday.tangledmonolith.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sellers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제거 - 판매자는 여러 상품을 구매할 수 있기 때문에 product쪽에 SellerId를 두는게 적합
    //@OneToMany(mappedBy = "seller")
    //private List<Product> products = new ArrayList<>();

    // 연관관계를 끊기
    private Long userId;

    public static Seller create(Long userId) {
        Seller seller = new Seller();
        seller.userId = userId;
        
        return seller;
    }
}
