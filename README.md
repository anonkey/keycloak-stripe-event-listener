# Keycloak Extension - Stripe Event Listener

Create a stripe user at user creation and store stripeId in user attributes

### Generate JAR:
```shell
make
```

### Docker compose

Fill your stripe api key in `STRIPE_API_KEY` env var inside `keycloak` container

Mount jar file inside `keycloak` container

```yaml
    volumes:
      - type: bind
        source: ./keycloak-stripe-event-listener/target/stripe-event-listener.jar
        target: /opt/jboss/keycloak/standalone/deployments/stripe-event-listener.jar
```


### Run on Docker:
```shell
make docker-up
```

### Stop Docker:
```shell
make docker-down
```
