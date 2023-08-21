package itm;

import javax.servlet.FilterChain;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import javax.servlet.http.HttpFilter;

@WebFilter(value = "/time-templated")
public class TimezoneValidateFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req,
                            HttpServletResponse resp,
                            FilterChain chain) throws IOException {

        String tz = req.getParameter("timezone");
        if ( tz == null ) tz="UTC";
        try {
            ZoneId.of(tz);
            chain.doFilter(req, resp);
        } catch (Exception e) {
            resp.setStatus(400);
            resp.setContentType("text/html");
            resp.getWriter().write("Invalid timezone");
            resp.getWriter().close();
        }
    }
}
