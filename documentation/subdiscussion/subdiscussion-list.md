# List SubDiscussions by Discussion Id

Lists all sub-discussions under the discussion id.

**URL**: `/subdiscussion/{discussionId}`

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
        "discussionId": "discussionId",
        "updated": "update time",
        "created": "created time"
    },
    {
        "id": "randomId",
        "title": "title",
        "discussionId": "discussionId",
        "updated": "update time",
        "created": "created time"
    }
]
```

## Error Response

**Condition**: If subdiscussion is not found by 'discussionId'.

**Code**: `404 NOT FOUND`

```json
{
    "timestamp": "time",
    "path": "/subdiscussion/{discussionId}",
    "status": 404,
    "error": "Not Found",
    "message": "",
    "requestId": "id"
}
```