package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @Slf4j
// HTTP Message body에 String을 담아서 반환하는 RestController
@RestController
public class LogTestController {
    
    // @Slf4j로 Logger를 선언해줄 수 있음.
    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        // System.out.println은 로그 레벨과 상관없이 모든 로그를 출력
        System.out.println("name = " + name);

        // 개발 서버는 debug 레벨부터 로그를 보통 출력한다.
        // 운영 서버는 info 레벨부터 로그를 출력하도록 하는 것이 좋다.
        log.trace("trace log = {}", name);

        // 이 방식으로 출력하면 trace 레벨의 로그를 출력할 일이 없어도 '+' 연산을 통해 리소스가 사용된다.
        // log.trace("trace log = " + name);

        log.debug("debug log = {}", name);
        log.info("info log = {}", name);
        log.warn("warn log = {}", name);
        log.error("error log = {}", name);

        return "ok";
    }

}
