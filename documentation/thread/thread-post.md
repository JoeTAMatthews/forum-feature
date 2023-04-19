# POST Thread

Saves a new thread from a user.

**URL**: `/thread`

**Method**: `POST`

**Auth Required**: YES

**Data constraints**

```json
{
    "title": "[valid title]",
    "content": "[valid content]"
}
```

## Success Response

**Code**: `200 OK`

**Content Example**

```json
{
    "id": "randomId",
    "title": "title",
    "content": "content",
    "ratings": [
        {
            "icon": "icon",
            "userId": "userId",
            "added": "added time"
        }
    ],
  
    "comments": [
      {
        "id": "randomId",
        "content": "content",
        "ratings": [
            {
                "icon": "icon",
                "userId": "userId",
                "added": "added time"
            }
        ],
        "threadId": "threadId",
        "replyCommentId": "replyCommentId or null",
        "userId": "userId",
        "edited": "edited time",
        "created": "created time"
      }
    ],
    "userId": "userId",
    "updated": "update time",
    "created": "created time"
}
```