# GET Thread

Gets a thread by its id.

**URL**: `/thread/{id}`

**Method**: `GET`

**Auth Required**: NO

## Success Response

**Code**: `200 OK`

**Content Example**

```json
{
    "id": "randomId",
    "title": "title",
    "content": "content",
    "sectionId": "sectionId",
    "updated": "update time",
    "created": "created time"
}
```

## Error Response

**Condition**: If the thread does not exist.

**Code**: `404 NOT FOUND`

**Content Example**

```json
{
    "timestamp": "time",
    "path": "/thread/{id}",
    "status": 404,
    "error": "Not Found",
    "message": "",
    "requestId": "id"
}
```