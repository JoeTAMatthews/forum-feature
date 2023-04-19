# DEL Section

Delete a section from the forum by the identifier.

**URL**: `/section/{id}`

**Method**: `DELETE`

**Auth Required**: YES

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

**Condition**: If section not found by 'id'.

**Code**: `404 NOT FOUND`

**Content**:

```json
{
    "timestamp": "time",
    "path": "/section/{id}",
    "status": 404,
    "error": "Not Found",
    "message": "",
    "requestId": "id"
}
```