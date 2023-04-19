# DELETE Comment

Deletes a comment by its id.

**URL**: `/comments/{id}`

**Method**: `DELETE`

**Auth Required**: YES

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
    "userId": "userId",
    "replyCommentId": "replyCommentId or null",
    "edited": "edited time",
    "created": "created time"
}
```