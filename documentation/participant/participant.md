# Participant Api (PART)

## Endpoint /v1/participant

| Url            |                         Action                         | Status |
|:---------------|:------------------------------------------------------:|:------:|
| GET /:id       |     Retrieves the participant for a participant id     |  200   |
| GET /:id/token | Generates a token for the participant for the given id |  200   |
| GET /          |               Retrieves all participants               |  200   |
| POST /         |                 Creates a participant                  |  201   |

## User Stories

### US PART 1: [API] Create Participant 🟢

#### Context

As an admin, I want to be able to create a participant.

#### Model

```json
{
  "first_name": "First",
  "last_name": "Last",
  "email": "first.last@email.net"
}
```

```java
public record CreateParticipant(
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("email_address")
        String emailAddress
) {
}
```

```sql
CREATE TABLE participants
(
    ID         UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    token      VARCHAR(255) NOT NULL,
    CREATED_AT TIMESTAMP WITHOUT TIME ZONE default now(),
    UPDATED_AT TIMESTAMP WITHOUT TIME ZONE
);
```

#### Usage

This operation is used to create a participant which is filled in from a modal.

### US PART 2: [UI] Create Participant Endpoint 🟢

#### Context

As a system, there needs to be an Endpoint that will call the participant api to create a participant.

#### Vaadin Endpoint: ParticipantAdminEndpoint

- AllowedRoles: ADMIN
- ParticipantApi.create(createActivity)

#### Operation

```java
ParticipantDetail create(CreateParticipant createParticipant);
```

#### Usage

This operation is used in the UI to create a participant.

### US PART 3: [API] List Participants 🟢

#### Context

As a system, there needs to be an Endpoint that will retrieve all participants.

#### Vaadin Endpoint: ParticipantAdminEndpoint

- AllowedRoles: ADMIN
- ParticipantApi.findAll()

#### Usage

This operation is used in the UI to retrieve all participants.

### US PART 4: [UI] List Participants Endpoint 🟢

#### Context

As a system, there needs to be an Endpoint that will call the participant api to create a participant.

#### Vaadin Endpoint: ParticipantAdminEndpoint

- AllowedRoles: ADMIN
- ParticipantApi.create(createActivity)

#### Operation

```java
List<ParticipantDetail> findAll();
```

#### Usage

This operation is used in the UI to retrieve all participants.

### US PART 5: [API] Generate Token 🟢

#### Context

As a system, there needs to be an Endpoint that generates a token for a participant.

#### Vaadin Endpoint: ParticipantAdminEndpoint

- AllowedRoles: ADMIN
- ParticipantApi.generateToken(participantId)

#### Usage

This operation is used in the UI to generate a token for a participant.

### US PART 6: [UI] List Participants Endpoint 🟢

#### Context

As a system, there needs to be an Endpoint that will call the participant api to create a participant.

#### Vaadin Endpoint: ParticipantAdminEndpoint

- AllowedRoles: ADMIN
- ParticipantApi.create(createActivity)

#### Operation

```java
List<ParticipantDetail> findAll();
```

#### Usage

This operation is used in the UI to retrieve all participants.

### US PART 7: [API] Generate Token 🟢

#### Context

As a system, there needs to be an Endpoint that generates a token for a participant.

#### Vaadin Endpoint: ParticipantAdminEndpoint

- AllowedRoles: ADMIN
- ParticipantApi.generateToken(participantId)

#### Usage

This operation is used in the UI to generate a token for a participant.

### US PART 8: [UI] Generate Token For Participant Endpoint 🟢

#### Context

As a system, there needs to be an Endpoint that generates an access token for a participant.

#### Vaadin Endpoint: ParticipantAdminEndpoint

- AllowedRoles: ADMIN
- ParticipantApi.generateToken(participantId)

#### Operation

```java
void generateToken(UUID participantId);
```

#### Usage

This operation is used in the UI to retrieve all participants.

### US PART 9: [UI] Participants Page 🔴

#### Context

As an admin, I want to have a secured page where the admin can find all participants listed in a table.

#### Data

From ParticipantAdminEndpoint.findAll() we can fetch all the participants and show the first name,last name and email.\
There a buttons as well:

- delete participant
- edit participant
- generate token
- view participant

### Roles

ADMIN

### US PART 10: [UI] Participant Page 🔴

#### Context

As an admin, I want to have a secured page where the admin can retrieve the Participant Detail.

#### Data

From ParticipantAdminEndpoint.findById(participantId) we can fetch all the information for the given participant id.\
There a buttons as well:

- delete participant
- edit participant
- generate token

### Actions

1. when delete participant button has been clicked, then the participant will be removed from the system and the page
   will be redirected to the Participants Page (US PART 9).
2. when the edit participant button has been clicked, then the page will transform in edit modus.
3. when generate token button has been clicked, than the token will be generated in the backend.

### Roles

ADMIN