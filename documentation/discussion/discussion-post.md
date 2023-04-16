# POST Discussion

Saves a new discussion.

**URL**: `/discussion`

**Method**: `POST`

**Auth Required**: YES

**Data constraints**

```json
{
    "title": "[valid title]",
    "sectionId": "[valid sectionId]"
}
```

## Success Response

**Code**: `200 OK`

**Content Example**

```json
{
    "id": "randomId",
    "title": "title",
    "sectionId": "sectionId",
    "updated": "update time",
    "created": "created time"
}
```

## Error Response

**Condition**: If 'title' is the same as discussion already saved.

**Code**: `302 FOUND`

```json
{
    "timestamp": "time",
    "path": "/discussion",
    "status": 302,
    "error": "Found",
    "message": "",
    "requestId": "id"
}
```