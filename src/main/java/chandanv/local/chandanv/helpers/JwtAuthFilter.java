package chandanv.local.chandanv.helpers;

import java.io.IOException;


import java.util.Map;
import java.util.HashMap;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import tools.jackson.databind.ObjectMapper;

import org.springframework.web.filter.OncePerRequestFilter;

import chandanv.local.chandanv.modules.users.services.impl.CustomUserDetailsService;
import chandanv.local.chandanv.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final ObjectMapper objectMapper;

    public JwtAuthFilter(JwtService jwtService, CustomUserDetailsService customUserDetailsService, ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
        this.objectMapper = objectMapper;
    }


    @Override
    protected boolean shouldNotFilter(
        @NotNull HttpServletRequest request
    ) {
        String path = request.getRequestURI();
        return path.startsWith("/api/v1/auth/login");
    }



    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userId;

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                // filterChain.doFilter(request, response);
                sendErrorResponse(response,
                        request,
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Xac thuc khong thanh cong",
                        "Khong tim thay Token"
                );
                return;
            }

            jwt = authHeader.substring(7);
            if (!jwtService.isTokenFormatValid(jwt)) {
                sendErrorResponse(response,
                        request,
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Xac thuc khong thanh cong",
                        "Token khong dung dinh dang"
                );
                return;
            }
            if(!jwtService.isSignatureValid(jwt)){
                sendErrorResponse(response,
                        request,
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Xac thuc khong thanh cong",
                        "Chu ky khong hop le"
                );
                return;
            }
            if(!jwtService.isIssuerToken(jwt)){
                sendErrorResponse(response,
                        request,
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Xac thuc khong thanh cong",
                        "Nguon token khong hop le"
                );
                return;
            }
            if(!jwtService.isTokenExpired(jwt)){
                sendErrorResponse(response,
                        request,
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Xac thuc khong thanh cong",
                        "Token het han"
                );
                return;
            }
            if(jwtService.isBlacklistedToken(jwt)){
                sendErrorResponse(response,
                        request,
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Xac thuc khong thanh cong",
                        "Token bi khoa "
                );
                return;
            }

            userId = jwtService.getUserIdFromJwt(jwt);
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);

                final String emailFromToken = jwtService.getEmailFromJwt(jwt);
                if(!emailFromToken.equals(userDetails.getUsername())){
                    sendErrorResponse(response,
                        request,
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Xac thuc khong thanh cong",
                        "User token khong chinh xac"
                );
                return;
                }
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.info("Xac thuc tai khoan thanh cong: " + userDetails.getUsername());
            }

            filterChain.doFilter(request, response);
        } catch (ServletException | IOException e) {
            sendErrorResponse(response,
                    request,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Network Error",
                    e.getMessage()
            );
        }

    }

    private void sendErrorResponse(
            @NotNull HttpServletResponse response,
            @NotNull HttpServletRequest request,
            int statusCode,
            String error,
            String message
    ) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json;charset=UTF8");

        Map<String, Object> errorResponse = new HashMap<>();

        errorResponse.put("timestamp", System.currentTimeMillis());
        errorResponse.put("status", statusCode);
        errorResponse.put("error", error);
        errorResponse.put("message", message);
        errorResponse.put("path", request.getRequestURI());

        String jsonResponse = objectMapper.writeValueAsString(errorResponse);

        response.getWriter().write(jsonResponse);

    }
}
