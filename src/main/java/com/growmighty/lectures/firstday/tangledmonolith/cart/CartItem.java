package com.growmighty.lectures.firstday.tangledmonolith.cart;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;

    public static CartItem create(Long productId, int quantity) {
        validateQuantity(quantity);

        CartItem item = new CartItem();
        item.productId = productId;
        item.quantity = quantity;
        return item;
    }

    // 기존 수량에 추가
    void increaseQuantity(int quantity) {
        validateQuantity(quantity);
        updateQuantity(this.quantity + quantity);
    }

    // 수량 변경
    void updateQuantity(int quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    // 수량 검증
    private static void validateQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("수량은 1 이상이어야 합니다. 현재 입력 값은 " + quantity);
        }
    }
    void assignCart(Cart cart) {
        this.cart = cart;
    }
}
