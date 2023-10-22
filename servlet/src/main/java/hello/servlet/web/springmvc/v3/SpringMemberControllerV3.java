package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    /*
    Save 하려고하는데 HTTP Method를 Get으로 날린다고 생각해보자.
    Get은 주로 조회에서 쓰이는건데 앞단의 캐시 문제가 발생할 수도 있고, HTTP Spec 상 Get을 여러 번 호출해도 문제 없기 때문에 사이드 이펙트가 발생할 우려가 있다.
    그래서 사이드 이펙트가 없는 단순 조회는 GET, 데이터를 변경하는 것은 POST로 맞춰주는 것이 좋다.
     */

    // @RequestMapping(value = "/new-form", method = RequestMethod.GET)
    @GetMapping("/new-form")
    public String newform() {
        return "new-form";
    }

    // @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PostMapping("/save")
    public String save(@RequestParam("username") String username, @RequestParam("age") int age, Model model) {

        // String username = request.getParameter("username");
        // int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        model.addAttribute("member", member);
        return "save-result";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String members(Model model) {
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);

        return "members";
    }

}
