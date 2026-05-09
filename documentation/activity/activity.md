# Activity Api (ACT)

## Endpoint /v1/activities

| Url                |                           Action                           | Status |
|:-------------------|:----------------------------------------------------------:|:------:|
| GET /:id/:username | Retrieves the activity for a specific question id and user |  200   |
| POST /             |                    Creates an activity                     |  200   |

## User Stories

### US ACT 1: [API] Create Activity 🟢

#### Context

As a participant, when I do some onscreen activities then the system will save those activities.

#### Model

```json
{
  "question_id": "252440ba-aced-4d77-b5c9-a93988027fee",
  "action_type": "INSERT",
  "element_id": "answerTextArea",
  "timestamp": 20321511,
  "additional_data": "Hello World"
}
```

```java
public record CreateActivity(
        @JsonProperty("question_id")
        UUID questionId,
        @JsonProperty("action_type")
        String actionType,
        @JsonProperty("element_id")
        String elementId,
        Instant timestamp,
        @JsonProperty("additional_data")
        String additionalData
) {
}
```

```sql
CREATE TABLE activities
(
    ID              UUID PRIMARY KEY,
    question_id     UUID         NOT NULL,
    element_id      VARCHAR(255) NOT NULL,
    action_type     VARCHAR(255) NOT NULL,
    username        VARCHAR(255) NOT NULL,
    additional_data TEXT         NOT NULL,
    action_time     TIMESTAMP,
    CREATED_AT      TIMESTAMP default now(),
    UPDATED_AT      TIMESTAMP
);
```

#### Usage

This operation is used in the UI with useActivityTracker.

### US ACT 2: [UI] Create Activity Endpoint 🟢

#### Context

As a system, there needs to be an Endpoint that will call the activity api to create an activity.

#### Vaadin Endpoint: ActivityEndpoint

- AllowedRoles: PARTICIPANT
- ActivityApi.create(createActivity)

#### Operation

```java
void log(List<CreateActivities> activities);
```

#### Usage

This operation is used in the UI with useActivityTracker.

### US ACT 3: [API] Get Activities by question id and username 🔴

#### Context

As a participant, when I do some onscreen activities then the system will save those activities.

#### Model

```json
{
  "question_id": "252440ba-aced-4d77-b5c9-a93988027fee",
  "action_type": "INSERT",
  "element_id": "answerTextArea",
  "timestamp": 20321511,
  "additional_data": "Hello World"
}
```

```java
public record CreateActivity(
        @JsonProperty("question_id")
        UUID questionId,
        @JsonProperty("action_type")
        String actionType,
        @JsonProperty("element_id")
        String elementId,
        Instant timestamp,
        @JsonProperty("additional_data")
        String additionalData
) {
}
```

```sql
CREATE TABLE activities
(
    ID              UUID PRIMARY KEY,
    question_id     UUID         NOT NULL,
    element_id      VARCHAR(255) NOT NULL,
    action_type     VARCHAR(255) NOT NULL,
    username        VARCHAR(255) NOT NULL,
    additional_data TEXT         NOT NULL,
    action_time     TIMESTAMP,
    CREATED_AT      TIMESTAMP default now(),
    UPDATED_AT      TIMESTAMP
);
```

#### Usage

To retrieve the activities done by a participant during answering the questions.

### US ACT 4: [UI] Get Activities By Question Id and Username Endpoint 🔴

#### Context

As a system, there needs to be an Endpoint that will retrieve all activities by question id.

#### Vaadin Endpoint: ActivityEndpoint

- AllowedRoles: ADMIN
- ActivityApi.findActivities(questionId, username)

#### Operation

```java
List<ActivityDetail> findActivities(UUID questionId, String username);
```

#### Usage

This operation is used to replay the actions for a specific question.

### US ACT 5: [UI] Screen Question View For Participant 🟠

#### Context

As a system, a page will contain the data from a question and displayed accordingly the type of question.

#### Vaadin Endpoint: ActivityEndpoint

- AllowedRoles: PARTICIPANT
- QuestionApi.findById(questionId)

#### Operation

```java
List<ActivityDetail> findActivities(UUID questionId, String username);
```

#### Usage

This operation is used to replay the actions for a specific question.