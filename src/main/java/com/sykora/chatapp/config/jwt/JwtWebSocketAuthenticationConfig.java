package com.sykora.chatapp.config.jwt;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class JwtWebSocketAuthenticationConfig implements WebSocketMessageBrokerConfigurer{

    private final JwtProvider jwtProvider;
    private UserDetailsService userDetailsService;

    public JwtWebSocketAuthenticationConfig(JwtProvider jwtProvider, UserDetailsService userDetailsService) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    List<String> authorization = accessor.getNativeHeader("Authorization");
                    String jwt = authorization.get(0).split(" ")[1];

                    String username = jwtProvider.extractUsername(jwt);
                    User user = (User) userDetailsService.loadUserByUsername(username);
                    if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt, user)) {
                        Principal principal = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        if (Objects.isNull(principal))
                            return null;
                        accessor.setUser(principal);
                    }
                }
                return message;
            }
        });
    }
}
