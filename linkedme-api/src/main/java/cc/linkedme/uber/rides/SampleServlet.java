/*
 * Copyright (c) 2015 Uber Technologies, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package cc.linkedme.uber.rides;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.api.client.auth.oauth2.Credential;
import cc.linkedme.uber.rides.auth.OAuth2Credentials;
import cc.linkedme.uber.rides.client.Session;
import cc.linkedme.uber.rides.client.UberRidesServices;
import cc.linkedme.uber.rides.client.UberRidesSyncService;
import cc.linkedme.uber.rides.client.model.UserProfile;

/**
 * Demonstrates how to authenticate the user and load their profile via a servlet.
 */
public class SampleServlet extends HttpServlet {

    private OAuth2Credentials oAuth2Credentials;
    private Credential credential;
    private UberRidesSyncService uberRidesService;

    /**
     * Clear the in memory credential and Uber API service once a call has ended.
     */
    @Override
    public void destroy() {
        uberRidesService = null;
        credential = null;

        super.destroy();
    }

    /**
     * Before each request, fetch an OAuth2 credential for the user or redirect them to the
     * OAuth2 login page instead.
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (oAuth2Credentials == null) {
            oAuth2Credentials = Server.createOAuth2Credentials();
        }

        // Load the user from their user ID (derived from the request).
        HttpSession httpSession = req.getSession(true);
        if (httpSession.getAttribute(Server.USER_SESSION_ID) == null) {
            httpSession.setAttribute(Server.USER_SESSION_ID, new Random().nextLong());
        }
        credential = oAuth2Credentials.loadCredential(httpSession.getAttribute(Server.USER_SESSION_ID).toString());

        if (credential != null && credential.getAccessToken() != null) {
            if (uberRidesService == null) {
                // Create the session
                Session session = new Session.Builder()
                        .setCredential(credential)
                        .setEnvironment(Session.Environment.PRODUCTION)
                        .build();

                // Set up the Uber API Service once the user is authenticated.
                uberRidesService = UberRidesServices.createSync(session);
            }

            super.service(req, resp);
        } else {
            resp.sendRedirect(oAuth2Credentials.getAuthorizationUrl());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Fetch the user's profile.
        UserProfile userProfile = uberRidesService.getUserProfile().getBody();

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().printf("Logged in as %s%n", userProfile.getEmail());
    }
}
