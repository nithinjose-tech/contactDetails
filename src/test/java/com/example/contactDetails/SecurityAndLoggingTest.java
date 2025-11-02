package com.example.contactDetails;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class demonstrates how to test Spring Security and SLF4J logging.
 * Note: For actual testing, you would run the application and make real HTTP requests.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityAndLoggingTest {

    private static final Logger logger = LoggerFactory.getLogger(SecurityAndLoggingTest.class);

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test accessing a protected endpoint without authentication.
     * This should return a 401 Unauthorized status.
     */
    @Test
    public void testUnauthenticatedAccess() throws Exception {
        logger.info("Testing unauthenticated access to protected endpoint");
        mockMvc.perform(get("/api/contacts"))
                .andExpect(status().isUnauthorized());
        logger.info("Unauthenticated access test completed successfully");
    }

    /**
     * Test accessing a protected endpoint with authentication.
     * This should return a 200 OK status.
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testAuthenticatedAccess() throws Exception {
        logger.info("Testing authenticated access to protected endpoint");
        mockMvc.perform(get("/api/contacts"))
                .andExpect(status().isOk());
        logger.info("Authenticated access test completed successfully");
    }

    /**
     * Test logging functionality.
     * This simply demonstrates how to use the SLF4J logger.
     */
    @Test
    public void testLogging() {
        logger.trace("This is a TRACE message");
        logger.debug("This is a DEBUG message");
        logger.info("This is an INFO message");
        logger.warn("This is a WARN message");
        logger.error("This is an ERROR message");
    }
}

// Made with Bob
