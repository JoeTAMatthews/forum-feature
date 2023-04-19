# POST Comment

Saves a new comment from a user.

**URL**: `/comments`

**Method**: `POST`

**Auth Required**: YES

**Data constraints**

```json
{
    "threadId": "[valid threadId]",
    "content": "[valid content]",
    "replyCommentId": "[replyCommentId or null]"
}
```

## Success Response

**Code**: `200 OK`

**Content Example**

```json
{
    "id": "randomId",
    "threadId": "threadId",
    "content": "content",
    "replyCommentId": "replyCommentId or null",
    
    "edited": "edited time",
    "created": "created time"
}
```