package com.workintech.s19d2.service;

import com.workintech.s19d2.entity.Member;
import com.workintech.s19d2.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Collection<? extends GrantedAuthority> authorities =
                member.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                        .toList();

        return new org.springframework.security.core.userdetails.User(
                member.getEmail(),
                member.getPassword(),
                authorities
        );
    }
}
