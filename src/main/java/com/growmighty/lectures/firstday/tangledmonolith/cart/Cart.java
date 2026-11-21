package com.growmighty.lectures.firstday.tangledmonolith.cart;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {
    private static final int MAX_DISTINCT_ITEMS = 50;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    @Column(nullable = false)
    private Long userId;

    public static Cart create(Long userId) {
        Cart cart = new Cart();
        cart.userId = userId;
        return cart;
    }

    // 상품 추가
    public void addItem(Long productId, int quantity) {
        for (CartItem item : items) {
            if (item.getProductId().equals(productId)) {
                item.increaseQuantity(quantity);
                return;
            }
        }
        if (items.size() >= MAX_DISTINCT_ITEMS) {
            throw new IllegalStateException(
                    "한 장바구니에 담을 수 있는 상품 종류는 최대 " + MAX_DISTINCT_ITEMS + "종류 입니다"
            );
        }

        CartItem item = CartItem.create(productId, quantity);
        item.assignCart(this);
        this.items.add(item);
    }

    // 상품 수량 변경
    public void changeQuantity(Long productId, int quantity) {
        CartItem item = findItem(productId);
        item.updateQuantity(quantity);
    }

    // 상품 삭제
    public void removeItem(Long productId) {
        CartItem item = findItem(productId);
        this.items.remove(item);
        item.assignCart(null);
    }

    // 상품 조회 및 존재 여부 검증
    private CartItem findItem(Long productId) {
        return items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("존재하지 않는 상품입니다."));
    }
}
