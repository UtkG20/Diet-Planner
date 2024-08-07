package com.utkarsh.dietplanner.security;

import com.utkarsh.dietplanner.service.ClientService;
import com.utkarsh.dietplanner.service.CustomUserDetailsService;
import com.utkarsh.dietplanner.service.DoctorService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Autowired
    private JwtHelper jwtHelper;


    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private DoctorService doctorService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

            String requestHeader = request.getHeader("Authorization");
            //Bearer 2352345235sdfrsfgsdfsdf
            logger.info(" Header :  {}", requestHeader);
            String username = null;
            String token = null;
            if (requestHeader != null && requestHeader.startsWith("Bearer")) {
                //looking good
                token = requestHeader.substring(7);
                try {

                    username = this.jwtHelper.getUsernameFromToken(token);

                } catch (IllegalArgumentException e) {
                    logger.info("Illegal Argument while fetching the username !!");
                    e.printStackTrace();
                } catch (ExpiredJwtException e) {
                    logger.info("Given jwt token is expired !!");
                    e.printStackTrace();
                } catch (MalformedJwtException e) {
                    logger.info("Some changed has done in token !! Invalid Token");
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();

                }


            } else {
                logger.info("Invalid Header Value !! ");
            }


            //
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//SecurityContextHolder.getContext().getAuthentication() --> this is to check whether this user is already authenticated or not
//if it is null that means we need to authenticate it

                logger.info("Inside Validation !!");

                //fetch user detail from username
                UserDetails userDetails;
                if (requestURI.startsWith("/auth/")){
                    userDetails = this.userDetailsService.loadUserByUsername(username);
                }else if(requestURI.startsWith("/client/")){
                    userDetails = this.clientService.loadUserByUsername(username);
                }else {
                    userDetails = this.doctorService.loadUserByUsername(username);
                }

                logger.info("Inside Validation with userId!!" + userDetails);

                Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
                if (validateToken) {

                    logger.info("Inside if" + validateToken);
                    //set the authentication
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);


                } else {
                    logger.info("Validation fails !!");
                }


            }


        logger.info("before filterChain.doFilter");
            try{
                filterChain.doFilter(request, response);
            }catch (Exception e){
                logger.info("Exception"+e);
            }
//        filterChain.doFilter(request, response);

        logger.info("after filterChain.doFilter"+ response);
    }
}
