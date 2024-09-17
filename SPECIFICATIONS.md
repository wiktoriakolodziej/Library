## Endpoints 

### Rental
#### Get All
`get take/rental`
- Get list of all rentals
- Sorting:
- delayed=true - shows only delayed rentals (due date before return date or return date is null and today date is after due date)
- afterRentalDate=2020-02-20 - shows only rentals rented after specified date
- beforeRentalDate=2020-02-20 - shows only rentals rented before specified date
- readerId=1 - shows only rentals of specified reader
- example response
```json
[
    {
        "id": 1,
        "rentalDate": "2024-09-17",
        "returnDate": null,
        "dueDate": "2024-09-26",
        "readerId": 1,
        "volumeIds": [
            1
        ]
    },
    {
        "id": 2,
        "rentalDate": "2023-09-17",
        "returnDate": "2023-09-27",
        "dueDate": "2023-09-26",
        "readerId": 1,
        "volumeIds": [
            1
        ]
    }
]
```

#### Get By Id
`get take/rental/{id}`
- Get rental of specified id
- example response
```json
{
    "id": 1,
    "rentalDate": "2024-09-17",
    "returnDate": null,
    "dueDate": "2024-09-26",
    "readerId": 1,
    "volumeIds": [
        1
    ]
}
```

#### Create
`post take/rental`
- Create rental
- Required parameters are: rentalDate and dueDate
- Returns created rental in response
- example request body
```json
{
    "readerId": 1,
    "rentalDate": "2022-09-17",
    "dueDate": "2022-09-26",
    "volumeIds": 
    [
        1
    ]
}
```
- example response
```json
{
    "id": 3,
    "rentalDate": "2022-09-17",
    "returnDate": null,
    "dueDate": "2022-09-26",
    "readerId": 1,
    "volumeIds": [
        1
    ]
}
```

#### Update
`put take/rental`
- Modify already created rental
- Parameters that can be changed are: returnDate and dueDate
- Request must contain rental id
- example request body
```json
{
    "id": 3,
    "returnDate": "2022-09-27"
}
```
- example response
```json
{
    "id": 3,
    "rentalDate": "2022-09-17",
    "returnDate": "2022-09-27",
    "dueDate": "2022-09-26",
    "readerId": 1,
    "volumeIds": [
        1
    ]
}
```

#### Delete
`delete take/rental/{id}`
- Delete rental of specified id

