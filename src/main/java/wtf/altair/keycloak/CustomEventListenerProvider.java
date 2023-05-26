package wtf.altair.keycloak;

import org.jboss.logging.Logger;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.UserModel;

import com.stripe.model.Customer;

public class CustomEventListenerProvider
        implements EventListenerProvider {
    private static final Logger log = Logger.getLogger(CustomEventListenerProvider.class);
    private final KeycloakSession session;
    private final RealmProvider model;

    public CustomEventListenerProvider(KeycloakSession session) {
        this.session = session;
        this.model = session.realms();
    }

    @Override
    public void close() {
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
    }

    @Override
    public void onEvent(Event event) {

        log.debugf("## NEW %s EVENT", event.getType());
        if (EventType.LOGIN.equals(event.getType())) {
            RealmModel realm = this.model.getRealm(event.getRealmId());
            UserModel user = this.session.users().getUserById(realm, event.getUserId());

            log.debugf("## User %s  event %s", user,
                    event);
            Customer stripeCustomer = StripeClient.createUser(user);

            if (stripeCustomer != null) {
                log.infof("Stripe customer for user %s created: %s", user.getId(), stripeCustomer.getId());

                user.setSingleAttribute("stripeId", stripeCustomer.getId());
                user.setEmailVerified(true);
            } else {
                log.errorf("Can't create stripe user for user: %s", user.getId());
            }

        }
    }
}
