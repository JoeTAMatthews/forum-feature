# DELETE Sub Discussion

Deletes a sub discussion.

**URL**: `/subdiscussion/{discussion}/{id}`

**Method**: `DELETE`

**Auth Required**: YES

## Success Response

**Code**: `200 OK`

**Content Example**

```json
{
    "id": "randomId",
    "title": "title",
    "discussionId": "discussionId",
    "updated": "update time",
    "created": "created time"
}
```

## Error Response

**Condition**: If sub discussion is not found by 'id'.

**Code**: `404 NOT FOUND`

**Content**:

```json
{
    "timestamp": "time",
    "path": "/subdiscussion/{discussion}/{id}",
    "status": 404,
    "error": "Not Found",
    "message": "",
    "requestId": "id"
}
```