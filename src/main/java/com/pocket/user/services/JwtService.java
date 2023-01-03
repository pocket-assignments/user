package com.app.pocket.services;

import com.app.pocket.dao.UserDao;
import com.app.pocket.models.User;
import com.app.pocket.pojo.JwtRequest;
import com.app.pocket.pojo.JwtResponse;
import com.app.pocket.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("Inside JwtService.loadUserName");
       User user = userDao.findById(userName).get();
       if(user != null ){
           return new org.springframework.security.core.userdetails.User(user.getUserName(),
                   user.getPassword(),
                   getAuthorities(user)
           );
       }else{
           throw new UsernameNotFoundException("Username not found");
       }
    }

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        System.out.println("Inside JwtService.createJwtToken");
        String userName = jwtRequest.getUserName();
        String userPassword = jwtRequest.getUserPassword();
        authenticate(userName, userPassword);

       final UserDetails userDetails = loadUserByUsername(userName);
       String newGeneratedToken = jwtUtil.generateToken(userDetails);

       User user = userDao.findById(userName).get();

       return new JwtResponse(user, newGeneratedToken);

    }

    private Set getAuthorities(User user){
        System.out.println("Inside JwtService.getAuthorities");
        Set authorities = new HashSet();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        });
        return authorities;
    }

    //user disabled or badCredential
    private void authenticate(String userName, String userPassword) throws Exception {
        System.out.println("Inside JwtService.authenticate");

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        }catch(DisabledException e){
            throw new Exception("User is disabled");
        }catch(BadCredentialsException e){
            throw new Exception("Bad credentials from user");
        }

    }
}
