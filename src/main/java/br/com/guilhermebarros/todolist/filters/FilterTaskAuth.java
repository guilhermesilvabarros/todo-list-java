package br.com.guilhermebarros.todolist.filters;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.guilhermebarros.todolist.repositories.IUsersRepository;
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
    private IUsersRepository usersRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        String authEncoded = authorization.substring("basic".length()).trim();

        byte[] authDecodedInBytes = Base64.getDecoder().decode(authEncoded);

        String authString = new String(authDecodedInBytes);

        String[] authCredentials = authString.split(":");

        String username = authCredentials[0];
        String password = authCredentials[1];

        var user = usersRepository.findByUsername(username);

        if (user == null) {
            response.sendError(401);
        } else {
            var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

            if (passwordVerify.verified) {
                chain.doFilter(request, response);
            } else {
                response.sendError(401);
            }
        }
    }
}
