# DELETE Thread

Deletes a thread by its id.

**URL**: `/thread/{id}`

**Method**: `DELETE`

**Auth Required**: YES

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

**Condition**: If thread is not found by 'id'.

**Code**: `404 NOT FOUND`

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