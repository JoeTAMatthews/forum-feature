# Create Section

Creates a section on the forum.

**URL**: `/section`

**Method**: `POST`

**Auth Required**: YES

**Data constraints**

```json
{
    "name": "[valid name]",
    "order": "[valid number]"
}
```

## Success Response

**Code**: `200 OK`

**Content Example**

```json
{
    "id": "randomId",
    "name": "name",
    "order": "orderNumber",
    "updated": "update time",
    "created": "created time"
}
```

## Error Response

**Condition**: If 'name' is the same as section already saved.

**Code**: `302 FOUND`

**Content**:

```json
{
    "timestamp": "time",
    "path": "/section",
    "status": 302,
    "error": "Found",
    "message": "",
    "requestId": "id"
}
```
