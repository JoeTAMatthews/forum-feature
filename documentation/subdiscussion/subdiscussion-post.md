# POST SubDiscussion

Create a sub-discussion under an existing discussion.

**URL**: `/subdiscussion`

**Method**: `POST`

**Auth Required**: YES

**Data constraints**

```json
{
    "title": "[valid title]",
    "discussionId": "[valid discussion id]"
}
```

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

**Condition**: If 'title' is the same as sub-discussion already saved.

**Code**: `302 FOUND`

**Content**:

```json
{
    "timestamp": "time",
    "path": "/subdiscussion",
    "status": 302,
    "error": "Found",
    "message": "",
    "requestId": "id"
}
```