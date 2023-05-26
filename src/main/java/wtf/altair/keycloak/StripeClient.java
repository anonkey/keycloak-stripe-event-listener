package wtf.altair.keycloak;

import org.keycloak.models.UserModel;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;

public class StripeClient {
    public static Customer createUser(UserModel user) {
        Stripe.apiKey = System.getenv("STRIPE_API_KEY");
        CustomerCreateParams params = CustomerCreateParams
                .builder()
                .setEmail(user.getEmail())
                .setName(user.getFirstName() + " " + user.getLastName())
                .build();

        try {
            Customer customer = Customer.create(params);

            return customer;
        } catch (StripeException e) {
            e.printStackTrace();

            return null;
        }
    }
}
