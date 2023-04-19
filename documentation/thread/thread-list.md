# List Thread by user

List all threads by user id.

**URL**: `/thread/user/{userId}`

**Method**: `GET`

**Auth Required**: NO

## Success Response

**Code**: `200 OK`

**Content Example**

```json
[
    {
        "id": "randomId",
        "title": "title",
        "content": "content",
        "sectionId": "sectionId",
        "userId": "userId",
        "updated": "update time",
        "created": "created time"
    },
    {
        "id": "randomId",
        "title": "title",
        "content": "content",
        "sectionId": "sectionId",
        "userId": "userId",
        "updated": "update time",
        "created": "created time"
    }
]
```
---
# List all threads.

Fetches all threads.

**URL**: `/thread/all`

**Method**: `GET`

**Auth Required**: NO

## Success Response

**Code**: `200 OK`

**Content Example**

```json
[
    {
        "id": "randomId",
        "title": "title",
        "content": "content",
        "sectionId": "sectionId",
        "userId": "userId",
        "updated": "update time",
        "created": "created time"
    },
    {
        "id": "randomId",
        "title": "title",
        "content": "content",
        "sectionId": "sectionId",
        "userId": "userId",
        "updated": "update time",
        "created": "created time"
    }
]
```