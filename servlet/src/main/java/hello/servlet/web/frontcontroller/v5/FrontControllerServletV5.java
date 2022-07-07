package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter()); // V3 컨트롤러 어댑터 탑재
        handlerAdapters.add(new ControllerV4HandlerAdapter()); // V4 컨트롤러 어댑터 탑재
    }

    private void initHandlerMappingMap() {
        // V3 추가
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
        
        // V4 추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // iniHandlerMappingMap()을 통해 담은 URI 요청을 기반으로 handler를 조회.
        Object handler = getHandler(request); // 1. handler 조회 (V5에서 handler는 컨트롤러 역할)

        if(handler == null) { // 만약 요청하는 URI에 반환되는 controller가 없다면
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 NOT FOUND
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler); // 2. handler adapter를 가져옴.

        ModelView mv = adapter.handle(request, response, handler); // 3~5. handler 호출, 그리고 ModelView 반환.

        String viewName = mv.getViewName(); // 핸들러 어댑터에게서 반환받은 ModelView에서 논리이름을 추출함. ex) new-form
        MyView view = viewResolver(viewName); // 6~7. viewResolver 호출해서 MyView 반환받음.

        view.render(mv.getModel(), request, response); // 8. render(model) 호출.
    }

    private MyView viewResolver(String viewName) {
        // 실제 경로인 물리이름을 만들어서 반환 ex) /WEB-INF/views/new-form.jsp
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) { // 2. 핸들러를 처리할 수 있는 핸들러 어댑터 조회
        for(MyHandlerAdapter adapter : handlerAdapters) {
            if(adapter.supports(handler)) { // handler에 맞는 adapter를 지원하면
                return adapter; // 해당 adapter를 주입
            }
        }

        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI(); // 클라이언트가 요청한 URI 받기
        return handlerMappingMap.get(requestURI); // 1. 핸들러 매핑 정보에서 handler 조회
    }

}
