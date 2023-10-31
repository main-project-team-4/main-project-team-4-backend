package com.example.demo.utils;

import com.example.demo.member.entity.Member;
import com.example.demo.security.UserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockPrincipalSecurityContextFactory implements WithSecurityContextFactory<WithMockPrincipal> {
    @Override
    public SecurityContext createSecurityContext(WithMockPrincipal annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UserDetails principal = fromAnnotation(annotation);
        Authentication auth = UsernamePasswordAuthenticationToken.authenticated(
                principal, principal.getPassword(), principal.getAuthorities()
        );
        context.setAuthentication(auth);
        return context;
    }

    private UserDetails fromAnnotation(WithMockPrincipal annotation) {
        Member member = new Member();
        member.setUsername(annotation.username());
        member.setPassword(annotation.password());
        member.setNickname(annotation.nickname());
        return new UserDetailsImpl(member);
    }
}
