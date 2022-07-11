package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.*;

@RestController
public class MappingClassController {

    /**
     * GET /mapping/users
     */
    @GetMapping("/mapping/users")
    public String users() {
        return "get users";
    }

    /**
     * POST /mapping/users
     */
    @PostMapping("/mapping/users")
    public String addUser() {
        return "post user";
    }

    /**
     * GET /mapping/users/{userId}
     * @PathVariable 의 이름과 파라미터 이름이 같으면 생략할 수 있다.
     */
    @GetMapping("/mapping/users/{userId}")
    public String findUser(@PathVariable String userId) {
        return "get userId = " + userId;
    }

    /**
     * PATCH /mapping/users/{userId}
     */
    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String userId) {
        return "update userId = " + userId;
    }

    /**
     * DELETE /mapping/users/{userId}
     */
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        return "delete userId = " + userId;
    }

}
