package hello.itemservice.domain.item;

import lombok.Data;

@Data // @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode 등이 전부 포함 되어 있어 사용 시 유의
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

}
