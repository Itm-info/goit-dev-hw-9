package itm;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/time-templated")
public class ThymeleafTestController extends HttpServlet {
    private TemplateEngine engine;

    @Override
    public void init() {
        engine = new TemplateEngine();

        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("./templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }

    private String getCookie(HttpServletRequest req, String name, String defaultValue) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return defaultValue;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");

        String tz = req.getParameter("timezone");
        if ( tz == null ) tz = getCookie(req, "timezone", "UTC");
        else              resp.addCookie(new Cookie("timezone", tz));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss OOOO");
        String time = OffsetDateTime.now(ZoneId.of(tz)).format(formatter);

        Map<String, Object> params = new LinkedHashMap<>();
        params.put("time", time);
        params.put("timezone", tz);

        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("queryParams", params)
        );

        engine.process("test", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}