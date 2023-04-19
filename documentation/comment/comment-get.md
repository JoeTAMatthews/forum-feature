# GET Comment

Gets a comment by its id.

**URL**: `/comments/{id}`

**Method**: `GET`

**Auth Required**: NO

## Success Response

**Code**: `200 OK`

**Content Example**

```json
{
    "id": "randomId",
    "threadId": "threadId",
    "content": "content",
    "ratings": [
        {
            "icon": "icon",
            "userId": "userId",
            "added": "added time"
        }
    ],
    "replyCommentId": "replyCommentId or null",
    "userId": "userId", 
    "edited": "edited time",
    "created": "created time"
}
```

## Error Response

**Condition**: If 'id' is not a valid comment id.

**Code**: `404 NOT FOUND`

```json
{
    "timestamp": "time",
    "path": "/comments/{id}",
    "status": 404,
    "error": "Not Found",
    "message": "",
    "requestId": "id"
}
```