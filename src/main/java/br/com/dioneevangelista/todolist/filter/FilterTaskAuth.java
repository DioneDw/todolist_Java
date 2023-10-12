package br.com.dioneevangelista.todolist.filter;


import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.dioneevangelista.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository iUserRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var servletPath = request.getServletPath();
        if(servletPath.equals("/tasks/")){
            var authorization = request.getHeader("Authorization");
            var authEncoded = authorization.substring("Basic".length()).trim();

            System.out.println("Authorization Encoded: ");
            System.out.println(authEncoded);

            byte[] authDecode = Base64.getDecoder().decode(authEncoded);
            System.out.println("Authorization Decoded: ");
            System.out.println(authDecode);

            var authString = new String(authDecode);
            System.out.println("Byte to String: ");
            System.out.println(authString);

            String [] credentials = authString.split(":");
            String userName= credentials[0];
            String password= credentials[1];

            System.out.println("UserName: " + userName);
            System.out.println("Password: " + password);

            System.out.println("Validando user e password ...");
            var user = this.iUserRepository.findByUserName(userName);
            if(user == null){
                response.sendError(401,"Usuário não autorizado");
            }else{
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(),user.getPassword());
                if(passwordVerify.verified){
                    filterChain.doFilter(request,response);
                    System.out.println("Passou no filtro...");
                }else{
                    response.sendError(401,"Senha incorreta.");
                }
            }
        }else{
            filterChain.doFilter(request,response);
        }




    }
}

