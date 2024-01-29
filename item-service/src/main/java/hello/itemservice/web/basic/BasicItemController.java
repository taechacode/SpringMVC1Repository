package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    /* @RequiredArgsConstructor로 자동 생성됨.
    public BasicItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    */

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    // 스프링부트 3.2 버전부터 자바 컴파일러에 -parameters 옵션을 넣어주어야 애노테이션에 적는 이름을 생략할 수 있다.
    // [Settings] - [Build, Execution, Deployment] - [Compiler] - [Java Compiler]에서 [Additional command line parameters]에서 '-parameters'를 넣어준다.
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    // @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model){

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    // @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model){

        /* 파라미터의 ModelAttribute가 아래의 코드를 대신해준다.
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
         */

        itemRepository.save(item);
        // model.addAttribute("item", item); // 파라미터의 ModelAttribute가 코드를 대신해준다.

        return "basic/item";
    }

    // @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, Model model){

        /* 파라미터에 Model의 이름을 지정해주지 않으면 어떻게 될까?
        Item -> item
        클래스의 첫 글자를 소문자로 바꾼 것을 이름으로 사용한다.

        @ModelAttribute HelloData item의 경우에는..
        HelloData -> helloData
        model.addAttribute("helloData", item);
         */

        itemRepository.save(item);
        // model.addAttribute("item", item); // 파라미터의 ModelAttribute가 코드를 대신해준다.

        return "basic/item";
    }

    @PostMapping("/add")
    public String addItemV4(Item item){
        itemRepository.save(item);
        return "basic/item";
    }

    /*
    * 테스트용 데이터 추가
    */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

}
