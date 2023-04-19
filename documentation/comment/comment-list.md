# List Comments by thread id

List all comments by thread id.

**URL**: `/comments/thread/{threadId}`

**Method**: `GET`

**Auth Required**: NO

## Success Response

**Code**: `200 OK`

**Content Example**

```json
[
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
    },
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
]
```
---
# List Comments by user id

List all comments by user id.

**URL**: `/comments/user/{userId}`

**Method**: `GET`

**Auth Required**: NO

## Success Response

**Code**: `200 OK`

**Content Example**

```json
[
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
    },
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
]
```
---
# List Comments by reply comment id

List all comments by reply comment id.

**URL**: `/comments/reply/{replyCommentId}`

**Method**: `GET`

**Auth Required**: NO

## Success Response

**Code**: `200 OK`

**Content Example**

```json
[
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
    },
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
]
```

