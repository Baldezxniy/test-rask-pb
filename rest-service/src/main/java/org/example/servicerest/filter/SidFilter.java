package org.example.servicerest.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.servicerest.constants.AllowedSidList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class SidFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(SidFilter.class);

    @Override
    public void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws IOException {
        try {
            String sid = getRequestSid(request);
            if (sid == null || !AllowedSidList.sids.contains(sid)) {
                logger.warn("Unauthorized access attempt. Invalid SID: {}", sid);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Sid not valid");
                return;
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Error while processing SID validation", e);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        }
    }

    private String getRequestSid(HttpServletRequest request) {
        String sid = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(sid)) {
            return sid;
        }
        return null;
    }
}
