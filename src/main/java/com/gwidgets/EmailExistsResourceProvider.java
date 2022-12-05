package com.gwidgets;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;

public class EmailExistsResourceProvider implements RealmResourceProvider {

    private final KeycloakSession session;

    public EmailExistsResourceProvider(KeycloakSession session) {
        this.session = session;
    }


    @Override
    public Object getResource() {
        return new EmailExistsResource(session);
    }

    @Override
    public void close() {

    }

    public class EmailExistsResource {

        private final KeycloakSession session;

        public EmailExistsResource(KeycloakSession session) {
            this.session = session;
        }

        @GET
        public Result emailExists(@QueryParam("email") String email) {
            return new Result(session.users().getUserByEmail(session.getContext().getRealm(), email) != null);
        }
    }

    public class Result {

        private boolean exists;

        public Result(boolean exists) {
            this.exists = exists;
        }

        public boolean isExists() {
            return exists;
        }

        public void setExists(boolean exists) {
            this.exists = exists;
        }
    }
}
