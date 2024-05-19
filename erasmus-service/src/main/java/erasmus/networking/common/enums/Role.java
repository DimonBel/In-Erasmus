package erasmus.networking.common.enums;

public enum Role {
  NONE("ROLE_NONE"),
  STUDENT("ROLE_STUDENT"),
  ADMIN("ROLE_ADMIN"),
  OFFICIAL("ROLE_OFFICIAL");

  private final String roleApi;

  Role(String roleApi) {
    this.roleApi = roleApi;
  }

  public String getRoleString() {
    return this.roleApi;
  }

  public static Role fromString(String roleStr) {
    for (Role role : Role.values()) {
      if (roleStr.equals(role.getRoleString())) {
        return role;
      }
    }
    return Role.NONE;
  }
}
