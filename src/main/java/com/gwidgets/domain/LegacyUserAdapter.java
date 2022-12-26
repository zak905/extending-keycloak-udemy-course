package com.gwidgets.domain;

import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialModel;
import org.keycloak.models.ClientModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.SubjectCredentialManager;
import org.keycloak.models.UserModel;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class LegacyUserAdapter implements UserModel {


    private final String componentId;

    private final LegacyUser delegate;


    public LegacyUserAdapter(LegacyUser legacyUser, String componentId) {
        this.componentId = componentId;
        this.delegate = legacyUser;
    }

    @Override
    public String getId() {
        return "f:" + componentId + ":" + delegate.getId();
    }

    @Override
    public String getUsername() {
        return delegate.getEmail();
    }

    @Override
    public void setUsername(String username) {

    }

    @Override
    public Long getCreatedTimestamp() {
        return System.currentTimeMillis();
    }

    @Override
    public void setCreatedTimestamp(Long timestamp) {

    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void setEnabled(boolean enabled) {

    }

    @Override
    public void setSingleAttribute(String name, String value) {

    }

    @Override
    public void setAttribute(String name, List<String> values) {

    }

    @Override
    public void removeAttribute(String name) {

    }

    @Override
    public String getFirstAttribute(String name) {
        return null;
    }

    @Override
    public Stream<String> getAttributeStream(String name) {
        return Stream.of();
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        // to avoid an NPE, the map should not be empty, any version after 20.0.2, should not have this problem
        // I submitted a fix which was merged on 7 Dec. 2022: https://github.com/keycloak/keycloak/pull/9276
        return Map.of("feeling", List.of("good"));
    }

    @Override
    public Stream<String> getRequiredActionsStream() {
        return Stream.of();
    }

    @Override
    public void addRequiredAction(String action) {

    }

    @Override
    public void removeRequiredAction(String action) {

    }

    @Override
    public String getFirstName() {
        return delegate.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {

    }

    @Override
    public String getLastName() {
        return delegate.getLastName();
    }

    @Override
    public void setLastName(String lastName) {

    }

    @Override
    public String getEmail() {
        return delegate.getEmail();
    }

    @Override
    public void setEmail(String email) {

    }

    @Override
    public boolean isEmailVerified() {
        return true;
    }

    @Override
    public void setEmailVerified(boolean verified) {

    }

    @Override
    public Stream<GroupModel> getGroupsStream() {
        return Stream.of();
    }

    @Override
    public void joinGroup(GroupModel group) {

    }

    @Override
    public void leaveGroup(GroupModel group) {

    }

    @Override
    public boolean isMemberOf(GroupModel group) {
        return false;
    }

    @Override
    public String getFederationLink() {
        return null;
    }

    @Override
    public void setFederationLink(String link) {

    }

    @Override
    public String getServiceAccountClientLink() {
        return null;
    }

    @Override
    public void setServiceAccountClientLink(String clientInternalId) {

    }

    @Override
    public SubjectCredentialManager credentialManager() {
        return noOpCredentialManager();
    }

    @Override
    public Stream<RoleModel> getRealmRoleMappingsStream() {
        return Stream.of();
    }

    @Override
    public Stream<RoleModel> getClientRoleMappingsStream(ClientModel app) {
            return Stream.of();
    }

    @Override
    public boolean hasRole(RoleModel role) {
        // just a hack, to be able to impersonate the user
        // don't do this at home:)
        if (Objects.equals("manage-account", role.getName()) || Objects.equals("view-profile", role.getName())
                || Objects.equals("manage-account-links", role.getName())) {
            return true;
        }
        return false;
    }

    @Override
    public void grantRole(RoleModel role) {

    }

    @Override
    public Stream<RoleModel> getRoleMappingsStream() {
        return Stream.of();
    }

    @Override
    public void deleteRoleMapping(RoleModel role) {

    }

    private SubjectCredentialManager noOpCredentialManager() {
        return new SubjectCredentialManager() {
            @Override
            public boolean isValid(List<CredentialInput> inputs) {
                return Objects.equals(inputs.get(0).getChallengeResponse(), delegate.getPassword());
            }

            @Override
            public boolean updateCredential(CredentialInput input) {
                return false;
            }

            @Override
            public void updateStoredCredential(CredentialModel cred) {

            }

            @Override
            public CredentialModel createStoredCredential(CredentialModel cred) {
                return null;
            }

            @Override
            public boolean removeStoredCredentialById(String id) {
                return false;
            }

            @Override
            public CredentialModel getStoredCredentialById(String id) {
                return null;
            }

            @Override
            public Stream<CredentialModel> getStoredCredentialsStream() {
                return Stream.of();
            }

            @Override
            public Stream<CredentialModel> getStoredCredentialsByTypeStream(String type) {
                return Stream.of();
            }

            @Override
            public CredentialModel getStoredCredentialByNameAndType(String name, String type) {
                return null;
            }

            @Override
            public boolean moveStoredCredentialTo(String id, String newPreviousCredentialId) {
                return false;
            }

            @Override
            public void updateCredentialLabel(String credentialId, String credentialLabel) {

            }

            @Override
            public void disableCredentialType(String credentialType) {

            }

            @Override
            public Stream<String> getDisableableCredentialTypesStream() {
                return Stream.of();
            }

            @Override
            public boolean isConfiguredFor(String type) {
                return false;
            }

            @Override
            public boolean isConfiguredLocally(String type) {
                return false;
            }

            @Override
            public Stream<String> getConfiguredUserStorageCredentialTypesStream() {
                return Stream.of();
            }

            @Override
            public CredentialModel createCredentialThroughProvider(CredentialModel model) {
                return null;
            }
        };
    }
}
