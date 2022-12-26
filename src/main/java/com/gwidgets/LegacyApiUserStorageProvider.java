package com.gwidgets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gwidgets.domain.LegacyUser;
import com.gwidgets.domain.LegacyUserAdapter;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class LegacyApiUserStorageProvider implements UserStorageProvider, UserQueryProvider,
        UserLookupProvider, CredentialInputValidator {

    private final KeycloakSession session;

    private final String apiBaseUrl;

    private final String componentId;

    public LegacyApiUserStorageProvider(KeycloakSession session, String apiBaseUrl, String componentId) {
        this.session = session;
        this.apiBaseUrl = apiBaseUrl;
        this.componentId = componentId;
    }

    @Override
    public void close() {}

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, String search, Integer firstResult, Integer maxResults) {
        try {
            return SimpleHttp.doGet(apiBaseUrl + "/users", session).asJson(new TypeReference<List<LegacyUser>>() {
            }).stream().map((LegacyUser legacyUser) -> new LegacyUserAdapter(legacyUser, componentId));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Stream.of();
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, Map<String, String> params, Integer firstResult, Integer maxResults) {
        try {
            return SimpleHttp.doGet(apiBaseUrl + "/users", session).asJson(new TypeReference<List<LegacyUser>>() {
            }).stream().map((LegacyUser legacyUser) -> new LegacyUserAdapter(legacyUser, componentId));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Stream.of();
    }

    @Override
    public Stream<UserModel> getGroupMembersStream(RealmModel realm, GroupModel group, Integer firstResult, Integer maxResults) {
        return Stream.of();
    }

    @Override
    public Stream<UserModel> searchForUserByUserAttributeStream(RealmModel realm, String attrName, String attrValue) {
        return Stream.of();
    }

    @Override
    public UserModel getUserById(RealmModel realm, String id) {
        try {
            return SimpleHttp.doGet(apiBaseUrl + "/users", session).asJson(new TypeReference<List<LegacyUser>>() {
            }).stream().map((LegacyUser legacyUser) -> new LegacyUserAdapter(legacyUser, componentId))
                    .filter(user -> Objects.equals(user.getId(), id)).findFirst().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public UserModel getUserByUsername(RealmModel realm, String username) {
        try {
            return SimpleHttp.doGet(apiBaseUrl + "/users", session).asJson(new TypeReference<List<LegacyUser>>() {
            }).stream().map((LegacyUser legacyUser) -> new LegacyUserAdapter(legacyUser, componentId))
                    .filter(user -> Objects.equals(user.getUsername(), username)).findFirst().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public UserModel getUserByEmail(RealmModel realm, String email) {
        try {
            return SimpleHttp.doGet(apiBaseUrl + "/users", session).asJson(new TypeReference<List<LegacyUser>>() {
            }).stream().map((LegacyUser legacyUser) -> new LegacyUserAdapter(legacyUser, componentId))
                    .filter(user -> Objects.equals(user.getEmail(), email)).findFirst().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return Objects.equals(credentialType, PasswordCredentialModel.TYPE);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        return Objects.equals(credentialType, PasswordCredentialModel.TYPE);
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
        try {
            var foundUser = SimpleHttp.doGet(apiBaseUrl + "/users", session).asJson(new TypeReference<List<LegacyUser>>() {
            }).stream()
                    .filter(legacyUser -> Objects.equals(user.getEmail(), legacyUser.getEmail())).findFirst().get();

            return Objects.equals(foundUser.getPassword(), credentialInput.getChallengeResponse());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
