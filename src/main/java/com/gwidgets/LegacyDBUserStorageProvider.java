package com.gwidgets;

import com.gwidgets.domain.LegacyDBUser;
import com.gwidgets.domain.LegacyDBUserAdapter;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.GroupModel;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;

import javax.persistence.EntityManager;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class LegacyDBUserStorageProvider implements UserStorageProvider, UserQueryProvider,
        UserLookupProvider, CredentialInputValidator {

    private final EntityManager entityManager;

    private final String componentId;

    public LegacyDBUserStorageProvider(EntityManager entityManager, String componentId) {
        this.entityManager = entityManager;
        this.componentId = componentId;
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
        var id = user.getId().split(":")[2];

        LegacyDBUser legacyDBUser = this.entityManager.find(LegacyDBUser.class, id);
        if (legacyDBUser == null) {
            return false;
        }
        return Objects.equals(legacyDBUser.getPassword(), credentialInput.getChallengeResponse());
    }

    @Override
    public void close() {

    }

    @Override
    public UserModel getUserById(RealmModel realm, String id) {
        var userId = id.split(":")[2];
        var legacyUser = this.entityManager.find( LegacyDBUser.class, userId);
        if (legacyUser == null) {
            return null;
        }
        return new LegacyDBUserAdapter(this.componentId, legacyUser);
    }

    @Override
    public UserModel getUserByUsername(RealmModel realm, String username) {
        try {
            return new LegacyDBUserAdapter(componentId, this.entityManager.createNamedQuery("findByUsername", LegacyDBUser.class)
                    .setParameter("username", username)
                    .getSingleResult());
        } catch (Exception exception) {
            return null;
        }
    }

    @Override
    public UserModel getUserByEmail(RealmModel realm, String email) {
        try {
            return new LegacyDBUserAdapter(componentId, this.entityManager.createNamedQuery("findByEmail", LegacyDBUser.class)
                    .setParameter("email", email)
                    .getSingleResult());
        } catch (Exception exception) {
            return null;
        }

    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, String search, Integer firstResult, Integer maxResults) {
        return this.entityManager.createNamedQuery("findAll", LegacyDBUser.class).getResultStream().map(user -> new LegacyDBUserAdapter(componentId, user));
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, Map<String, String> params, Integer firstResult, Integer maxResults) {
        return this.entityManager.createNamedQuery("findAll", LegacyDBUser.class).getResultStream().map(user -> new LegacyDBUserAdapter(componentId, user));
    }

    @Override
    public Stream<UserModel> getGroupMembersStream(RealmModel realm, GroupModel group, Integer firstResult, Integer maxResults) {
        return Stream.of();
    }

    @Override
    public Stream<UserModel> searchForUserByUserAttributeStream(RealmModel realm, String attrName, String attrValue) {
        return Stream.of();
    }
}
