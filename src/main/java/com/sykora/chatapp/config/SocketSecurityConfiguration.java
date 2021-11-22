package com.sykora.chatapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class SocketSecurityConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer  {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .nullDestMatcher().authenticated()
                .simpMessageDestMatchers("/app/**").hasAnyRole("USER")
                .simpSubscribeDestMatchers("/app/**").hasAnyRole("USER")
                .anyMessage().authenticated();
    }

    //for test
    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
