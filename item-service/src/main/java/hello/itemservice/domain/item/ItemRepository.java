package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    // 실제 개발 시 HashMap을 사용하면 여러 스레드가 동시에 접근할 경우 동시성 문제 발생 우려가 있다.
    // 만약 동시성 문제를 해결하고 싶으면 ConcurrentHashMap을 사용해야 한다.
    private static final Map<Long, Item> store = new HashMap<>(); // static
    private static long sequence = 0L; // static

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);

        // 실제 개발에서는 다른 개발자가 id를 set 할 수 있는 위험이 있으므로
        // update를 위한 파라미터만 포함된 전용 DTO를 만드는 것이 좋다.
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }

}
