# List discussions by section id

Fetches all discussions by the section id.

**URL**: `/section/{sectionId}`

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
        "updated": "update time",
        "created": "created time"
    },
    {
        "id": "randomId",
        "title": "title",
        "content": "content",
        "sectionId": "sectionId",
        "updated": "update time",
        "created": "created time"
    }
]
```
---
# List all discussions.

Fetches all discussions.

**URL**: `/discussion/all`

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
        "updated": "update time",
        "created": "created time"
    },
    {
        "id": "randomId",
        "title": "title",
        "content": "content",
        "sectionId": "sectionId",
        "updated": "update time",
        "created": "created time"
    }
]
```