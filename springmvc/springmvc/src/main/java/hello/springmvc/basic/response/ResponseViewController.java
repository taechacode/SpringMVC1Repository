package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {

        // resources/templates/response/hello.html
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!");

        return mav;

    }

    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {

        model.addAttribute("data", "hello!");

        // @Controller에 String을 반환하면 View의 논리적인 이름을 반환하는 것과 같은 역할을 한다. (View Resolver 실행 및 렌더링)
        // 만약 @RequestMapping 위에 @ResponseBody를 사용했다면 "response/hello"가 화면에 그대로 출력될 것이다.
        // (@RestController도 마찬가지)
        return "response/hello";

    }

    // Controller의 Mapping 경로와 View 논리적인 경로가 같으면,
    // @Controller를 사용하고, HttpServletResponse, OutputStream(Writer)와 같은 HTTP 메시지 바디를 처리하는 파라미터가 없으면,
    // 요청 URL을 참고해서 논리적 View의 이름으로 Spring에서 인지한다.
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!");
    }

}
