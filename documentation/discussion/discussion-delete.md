# DELETE Discussion by identifier.

Deletes the discussion by the identifier.

**URL**: `/discussion/{id}`

**Method**: `DELETE`

**Auth Required**: YES

## Success Response

**Code**: `200 OK`

**Content Example**

```json
{
    "id": "randomId",
    "title": "title",
    "sectionId": "sectionId",
    "updated": "update time",
    "created": "created time"
}
```

## Error Response

**Condition**: If discussion with the given identifier does not exist.

**Code**: `404 NOT FOUND`

```json
{
    "timestamp": "time",
    "path": "/discussion/{id}",
    "status": 404,
    "error": "Not Found",
    "message": "",
    "requestId": "id"
}
```