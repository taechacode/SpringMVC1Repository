package hello.servlet.basic.request;

import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/* HTTP 요청 데이터 - API 메시지 바디 - 단순 텍스트 */

@WebServlet(name="requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // MessageBody의 내용을 바이트코드로 바로 얻어올 수 있음.
        ServletInputStream inputStream = request.getInputStream();

        // 스프링이 제공하는 유틸리티를 활용하여 바이트코드를 String으로 변환
        // 바이트코드를 문자열로 변환할 때 어떤 인코딩인지 알려줘야 한다. (여기서는 UTF-8)
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("messageBody = " + messageBody);

        response.getWriter().write("ok");

    }

}
