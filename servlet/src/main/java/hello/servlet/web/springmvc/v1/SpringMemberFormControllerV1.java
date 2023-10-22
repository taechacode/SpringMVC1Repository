package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Component // 컴포넌트 스캔을 통해 스프링 빈으로 등록
@RequestMapping // 클래스 레벨에 애노테이션을 사용해야한다. 밑에 메소드 레벨에 애노테이션을 작성하면 동작하지 않는다.
// @Controller
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process() {
        return new ModelAndView("new-form");
    }

}
