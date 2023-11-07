package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
        @RequestParam("username") String memberName,
        @RequestParam("age") int memberAge
    ) {
        log.info("username={}, age={}", memberName, memberAge);
        return "ok"; // view 조회를 진행하지 않고 Response Body에 메시지를 삽입
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            // localhost:8080/request-param-v3?username=hello&age=20
            @RequestParam String username,
            @RequestParam int age
    ) {
        log.info("username={}, age={}", username, age);
        return "ok"; // view 조회를 진행하지 않고 Response Body에 메시지를 삽입
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(
            // @RequestParam을 쓰지 않는 대신에 요청 파라미터 이름과 일치해야 함.
            String username,
            int age
    ) {
        log.info("username={}, age={}", username, age);
        return "ok"; // view 조회를 진행하지 않고 Response Body에 메시지를 삽입
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username, // required가 true이면 필수값으로 지정된다는 의미이다.
            // request-param?username= 으로 입력하면 null이 입력되는 것이 아니라 빈 문자가 입력된다.
            @RequestParam(required = false) Integer age // int는 null을 담을 수 없으므로 age 파라미터를 쓰지 않을거면 Integer를 써야한다.
    ) {
        log.info("username={}, age={}", username, age);
        return "ok"; // view 조회를 진행하지 않고 Response Body에 메시지를 삽입
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age // 기본값을 정수로 지정했기 때문에 age 파라미터를 지정하지 않아도 int형 사용 가능
    ) {
        log.info("username={}, age={}", username, age);
        return "ok"; // view 조회를 진행하지 않고 Response Body에 메시지를 삽입
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok"; // view 조회를 진행하지 않고 Response Body에 메시지를 삽입
    }

    @ResponseBody
    @RequestMapping("/request-param-multivalue-map")
    // http://localhost:8080/request-param-multivalue-map?username=kim&username=kang&age=25&age=30
    // username=[kim, kang], age=[25, 30]
    // 하나의 파라미터에 여러 값이 지정되면 'MultiValueMap'을 사용해 값을 받을 수 있다.
    // 파라미터의 값이 1개가 확실하다면 'Map'을 사용해도 되지만, 그렇지 않다면 'MultiValueMap'을 사용한다.
    public String requestParamMap(@RequestParam MultiValueMap<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok"; // view 조회를 진행하지 않고 Response Body에 메시지를 삽입
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    // public String modelAttributeV1(@RequestParam String username, @RequestParam int age) {
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        // HelloData helloData = new HelloData();
        // helloData.setUsername(username);
        // helloData.setAge(age);

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        log.info("helloData={}", helloData); // HelloData 클래스에 붙어있는 @Data에 @ToString이 들어있음.

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) { // @ModelAttribute 생략 가능.
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        log.info("helloData={}", helloData);

        // Spring은 위와 같이 파라미터에 애노테이션 생략 시 다음과 같은 규칙을 적용한다.
        // 'String', 'int', 'Integer'와 같은 단순 타입은 '@RequestParam'
        // 나머지는 '@ModelAttribute' (argument resolver로 지정해둔 타입 외)

        return "ok";
    }

}
