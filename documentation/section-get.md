# GET Section by Identifier

Gets a section for the forum.

**URL**: `/section/{id}`

**Method**: `GET`

**Auth Required**: NO

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
---
# GET Section by Name
Gets a section by the name from the forum.

**URL**: `/section/name/{name}`

**Method**: `GET`

**Auth Required**: NO

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

**Condition**: If section not found by 'name'.

**Code**: `404 NOT FOUND`

**Content**:

```json
{
    "timestamp": "time",
    "path": "/section/name/{name}",
    "status": 404,
    "error": "Not Found",
    "message": "",
    "requestId": "id"
}    
```