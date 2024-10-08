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
- Rentals with rental date before current date are forbidden
- Rental with due date before rental date are forbidden
- Can't rent a book that is currently rented, or that is not returned in time (if reader prolonged book without permission so return date is not set and due date is before today's date book is considered permanently unavailable)
- Returns created rental in response
- example request body
```json
{
    "readerId": 1,
    "rentalDate": "2024-09-17",
    "dueDate": "2024-09-26",
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
    "rentalDate": "2024-09-17",
    "returnDate": null,
    "dueDate": "2024-09-26",
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
    "returnDate": "2024-09-27"
}
```
- example response
```json
{
    "id": 3,
    "rentalDate": "2024-09-17",
    "returnDate": "2024-09-27",
    "dueDate": "2024-09-26",
    "readerId": 1,
    "volumeIds": [
        1
    ]
}
```

#### Delete
`delete take/rental/{id}`
- Delete rental of specified id

### Reader
#### Get All
`get take/reader`
- Get list of all readers
- example response
```json
[
    {
        "id": 1,
        "readerName": "domi",
        "readerSurname": "domi",
        "birthDate": "2017-03-20",
        "joiningDate": "2024-09-26",
        "penalty": 10.0
    },
    {
        "id": 2,
        "readerName": "domi",
        "readerSurname": "domi",
        "birthDate": "2017-03-20",
        "joiningDate": "2017-03-20",
        "penalty": 0.0
    }
]
```

#### Get By Id
`get take/reader/{id}`
- Get reader of specified id
- example response
```json
{
    "id": 2,
    "readerName": "domi",
    "readerSurname": "domi",
    "birthDate": "2017-03-20",
    "joiningDate": "2017-03-20",
    "penalty": 0.0
}
```

#### Create
`post take/reader`
- Create reader
- Required parameters are: readerName, readerSurname and birthDate
- Returns created reader in response
- Penalty is automatically set to 0.0 when creating a reader (but can be set manually to a different value)
- example request body
```json
{
    "readerName": "Jan",
    "readerSurname": "Kowalski",
    "birthDate": "2002-03-20",
    "joiningDate": "2017-03-20"
}
```
- example response
```json
{
    "id": 3,
    "readerName": "Jan",
    "readerSurname": "Kowalski",
    "birthDate": "2002-03-20",
    "joiningDate": "2017-03-20",
    "penalty": 0.0
}
```

#### Update
`put take/reader`
- Modify already created reader
- Parameters that can be changed are: readerName, readerSurname, birthDate, joiningDate and penalty
- Request must contain reader id
- example request body
```json
{
    "id": 3,
    "penalty": 10,
    "joiningDate": "2024-09-26"
}
```
- example response
```json
{
    "id": 3,
    "readerName": "Jan",
    "readerSurname": "Kowalski",
    "birthDate": "2002-03-20",
    "joiningDate": "2024-09-26",
    "penalty": 10.0
}
```

#### Delete
`delete take/reader/{id}`
- Delete reader of specified id and also all its rentals

### Book
#### Get All
`get take/book`
- Get list of all books and their volumes
- Sorting:
- authorSurname=N - sort by author surname
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
`get take/book/{id}`
- Get book of specified id
- example response
```json
{
    "id": 2,
    "title": "Hobbit",
    "authorName": "John Ronald Reuel",
    "authorSurname": "Tolkien",
    "version": 1,
    "description": "czyli tam i z powrotem",
    "volumes": null
}
```

#### Create
`post take/book`
- Create book
- Required parameters are: title, authorName and authorSurname
- Returns created book in response
- example request body
```json
{
    "title": "Hobbit",
    "authorName": "John Ronald Reuel",
    "authorSurname": "Tolkien",
    "description": "czyli tam i z powrotem",
    "version": 1
}
```
- example response
```json
{
    "id": 2,
    "title": "Hobbit",
    "authorName": "John Ronald Reuel",
    "authorSurname": "Tolkien",
    "version": 1,
    "description": "czyli tam i z powrotem",
    "volumes": null
}
```

#### Update
`put take/book`
- Modify already created book
- Parameters that can be changed are: title, authorName, authorSurname, version and description
- Request must contain book id
- example request body
```json
{
    "id": 1,
    "description": "Zmiana opisu",
    "authorSurname": "Milne"
}
```
- example response
```json
{
    "id": 1,
    "title": "Kubus",
    "authorName": "A",
    "authorSurname": "Milne",
    "version": 3,
    "description": "Zmiana opisu"
}
```

#### Delete
`delete take/book/{id}`
- Delete book of specified id and also its volumes

### Volume
#### Get All
`get take/volume`
- Get list of all volumes
- Sorting:
- available=true - shows only volumes available to rent by today
- example response
```json
[
    {
        "id": 1,
        "yearOfPublication": 3000,
        "bookCover": "hardcover",
        "pages": 100,
        "condition": "bad",
        "book": {
            "id": 1,
            "title": "Kubus",
            "authorName": "A",
            "authorSurname": "Milne",
            "version": 3,
            "description": "Zmiana opisu"
        }
    },
    {
        "id": 2,
        "yearOfPublication": 3000,
        "bookCover": "hardcover",
        "pages": 100,
        "condition": "bad",
        "book": {
            "id": 2,
            "title": "Hobbit",
            "authorName": "John Ronald Reuel",
            "authorSurname": "Tolkien",
            "version": 1,
            "description": "czyli tam i z powrotem"
        }
    }
]
```

#### Get By Id
`get take/volume/{id}`
- Get volume of specified id
- example response
```json
{
    "id": 1,
    "yearOfPublication": 3000,
    "bookCover": "hardcover",
    "pages": 100,
    "condition": "bad",
    "book": {
        "id": 1,
        "title": "Hobbit",
        "authorName": "John Ronald Reuel",
        "authorSurname": "Tolkien",
        "version": 1,
        "description": "czyli tam i z powrotem"
    }
}
```

#### Create
`post take/volume`
- Create volume
- Returns created volume in response
- example request body
```json
{
    "bookId": 1,
    "yearOfPublication": 3000,
    "pagess": 100,
    "bookCover": "hardcover",
    "condition": "bad"
}
```
- example response
```json
{
    "id": 2,
    "yearOfPublication": 3000,
    "bookCover": "hardcover",
    "pages": 100,
    "condition": "bad",
    "book": {
        "id": 1,
        "title": "Hobbit",
        "authorName": "John Ronald Reuel",
        "authorSurname": "Tolkien",
        "version": 1,
        "description": "czyli tam i z powrotem"
    }
}
```

#### Update
`put take/volume`
- Modify already created volume
- Return created volume in response
- example request body
```json
{
    "id": 1,
    "condition": "good"
}
```
- example response
```json
{
    "id": 1,
    "yearOfPublication": 3000,
    "bookCover": null,
    "pages": 100,
    "condition": "good",
    "book": {
        "id": 2,
        "title": "Kubus",
        "authorName": "A",
        "authorSurname": "M",
        "version": 3,
        "description": "Pierwszy opis Kubusia"
    }
}
```

#### Delete
`delete take/volume/{id}`
- Delete volume of specified id

