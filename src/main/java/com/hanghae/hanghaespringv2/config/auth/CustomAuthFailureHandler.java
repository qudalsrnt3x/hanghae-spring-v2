package com.hanghae.hanghaespringv2.config.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {


        response.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();

        writer.println("<script>");
        writer.println("alert('아이디 또는 패스워드를 다시 입력해주세요.')");
        writer.println("history.back()");
        writer.println("</script>");

        writer.flush();
        // flush() 다음에는 다른 메서드 사용할 수 없다. 이미 보내지기 때문...
    }
}
