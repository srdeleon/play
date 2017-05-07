package utils;

import play.Application;
import securesocial.core.Identity;
import securesocial.core.IdentityId;
import securesocial.core.java.BaseUserService;
import securesocial.core.java.Token;

import java.util.HashMap;
import java.util.Map;

public class SimpleUserService extends BaseUserService {
  private HashMap<String, Identity> users = new HashMap<String, Identity>(); 
  private HashMap<String, Token> tokens = new HashMap<String, Token>(); 

  public SimpleUserService(Application application) { 
    super(application);
  }

  @Override
  public Identity doSave(Identity user) {
    users.put(user.identityId().userId() + user.identityId().providerId(), user);
    return user;
  }

  @Override
  public void doSave(Token token) {
    tokens.put(token.uuid, token);
  }

  @Override
  public Identity doFind(IdentityId identityId) {
    return users.get(identityId.userId() + identityId.providerId());
  }

  @Override
  public Token doFindToken(String tokenId) {
    return tokens.get(tokenId);
  }

  @Override
  public Identity doFindByEmailAndProvider(String email,
                                           String providerId) {
    for (Identity user : users.values()) {
      if(user.identityId().providerId().equals(providerId) &&
          user.email().isDefined() && user.email().get()
          .equalsIgnoreCase(email)) {
        return user;
      }
    }
    return null;
  }

  @Override
  public void doDeleteToken(String uuid) {
    tokens.remove(uuid);
  }

  @Override
  public void doDeleteExpiredTokens() {
    for (Map.Entry<String, Token> entry : tokens.entrySet()) {
      if(entry.getValue().isExpired()) {
       tokens.remove(entry.getKey());
      }
    }
  }
}
