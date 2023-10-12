package hello.servlet.web.servletmvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Controller를 통해서 JSP파일을 부르고 싶다면 WEB-INF 하위 경로에 JSP 파일을 위치시키면 된다.
        // 그러면 절대 경로를 직접 주소 표시줄에 입력해서는 해당 JSP 파일을 불러올 수 없고,
        // 컨트롤러를 통해서만 해당 JSP 파일을 불러올 수 있다.
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }

}
