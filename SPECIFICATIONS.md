# Data Interface Specifications

## Endpoints (Servlets)
### Login
The login will be implemented according to the instructions:
- [Backend](https://www.bezkoder.com/spring-boot-login-example-mysql/)
- [Frontend](https://www.bezkoder.com/angular-17-jwt-auth/)
- [Fullstack](https://www.bezkoder.com/angular-17-spring-boot-jwt-auth/)

### Roles
public enum ERole {

    ROLE_USER, (user)
    
    ROLE_MODERATOR,
    
    ROLE_ADMIN, (admin)
    
    ROLE_EMPLOYEE, (emp)
    
    ROLE_EMPLOYEE_HR, (empHR)
    
    ROLE_EMPLOYEE_MECHANIC (empMech)
    
}

if you want to create new user provide names in brackets (in JSON)

### Employees
###### Package into JSON
`get api/employee`
- A list of all Employees and their respective IDs
```json
[
   {
    "id": 4,
    "office": {
        "id": 10,
        "location": "Warszawa"
    },
    "officeId": null,
    "salary": 0.0,
    "shiftStart": "08:00:00",
    "shiftEnd": "16:00:00",
    "email": "dom47@gmail.com",
    "name": "Domi",
    "lastName": "wasil",
    "role": "ROLE_EMPLOYEE_HR"
}
]
```

### Employee
###### Package into JSON
`get api/employee/id`

For a provided Employee ID:
```json

    {
    "id": 4,
    "office": {
        "id": 10,
        "location": "Warszawa"
    },
    "officeId": null,
    "salary": 0.0,
    "shiftStart": "08:00:00",
    "shiftEnd": "16:00:00",
    "email": "dom47@gmail.com",
    "name": "Domi",
    "lastName": "wasil",
    "role": "ROLE_EMPLOYEE_HR"
}

```
### DeleteEmployee
`delete api/employee/id`

returns http status ok if employee was succesfully deleted

### AddEmployee
`post api/auth/signup/employee`

```json
{
  "username": "Domi10",
  "email": "domi10@gmail.com",
  "password": "Domii11i1",
  "name": "Domi", //optional
  "lastName": "Domi",	//optional
  "role": "emp"	//optional default is emp
}
```

returns employee with id if it was sucessfulyy added
example result:

```json
{
    "id": 3,
    "office": null,
    "officeId": null,
    "salary": 0.0,
    "shiftStart": null,
    "shiftEnd": null,
    "email": "domi10@gmail.com",
    "name": "Domi",
    "lastName": "Domi",
    "role": "ROLE_EMPLOYEE"
}
```

### ModifyEmployee
`put api/employee`

employee sent in json must have id param, other params can be empty

modified employee is sent in response

Example request parameters:
```json
{
    "id": 6,
    "officeId": 2,
    "salary": 1000.0,
    "shiftStart": "08:00:00",
    "shiftEnd": "16:00:00"
}
```
###### Package into JSON
Response would be:
```json

{
    "id": 6,
    "office": {
        "id": 2,
        "location": "Nowy Sacz"
    },
    "officeId": null,
    "salary": 1000.0,
    "shiftStart": "08:00:00",
    "shiftEnd": "16:00:00",
    "email": "dom49@gmail.com",
    "name": "Domi",
    "lastName": null,
    "role": "ROLE_EMPLOYEE"

}
```
### Offices
### Get all offices
###### Package into JSON
`get api/office`
- A list of all offices and their respective IDs
```json
{
[
    {
        "id": 2,
        "location": "Nowy Sacz"
    },
    {
        "id": 3,
        "location": "Gliwice"
    },
    {
        "id": 4,
        "location": "Warszawa"
    }
]
}
```
### Get one office by id
###### Package into JSON
`get api/office/id`
- An office of given id or 404 not found message if office doesn't exist
```json
{
    "id": 2,
    "location": "Nowy Sacz"
}
```
### Create office
###### Package into JSON
`post api/office`
- location must not be blank, it isn't possible to create 2 offices of the same location
```json
{
    "location": "Katowice"
}
```
- response would be
```json
{
    "id": 11,
    "location": "Katowice"
}
```

### Delete office
###### Package into JSON
`delete api/office/id`
- returns status OK if office was successfully deleted
  
### Update office
###### Package into JSON
`put api/office`
- office sent in json must have id param, you can't change location to a location that already exists
 ```json
  {
    "id": 5,
    "location": "Gliwice"
}
```
- in reponse you get udpated office
 ```json
  {
    "id": 5,
    "location": "Gliwice"
}
```  

## Car
### Get all cars
###### Package into JSON
`get api/car`
- A list of all cars and their respective IDs
```json
{
  "id": 16,
  "cost": 21.37,
  "deposit": 8.9,
  "insuranceNumber": 1,
  "available": true,
  "model": "szympki",
  "office": {
    "id": 1,
    "location": "Nowy Sacz"
  },
  "officeId": null,
  "seatNumber": 1,
  "technicalStatus": false,
  "fuelType": null,
  "photo": null,
  "type": null,
  "licence": null
}
```
### Get one car by id
###### Package into JSON
`get api/car/id`
- An car of given id or 404 not found message if office doesn't exist
```json
{
  "id": 16,
  "cost": 21.37,
  "deposit": 8.9,
  "insuranceNumber": 1,
  "available": true,
  "model": "szympki",
  "office": {
    "id": 1,
    "location": "Nowy Sacz"
  },
  "officeId": null,
  "seatNumber": 1,
  "technicalStatus": false,
  "fuelType": null,
  "photo": null,
  "type": null,
  "licence": null
}
```
### Create car
###### Package into JSON
`post api/car`
- All paremeters except officeId, photo, fuleType, type and license must not be blank.
```json
{
  "cost":21.37,
  "deposit": 8.9,
  "insuranceNumber":1,
  "available":true,
  "model":"szympki",
  "seatNumber":1,
  "technicalStatus":false
}
```
- response would be
```json
{

  "id": 17,
  "cost": 21.37,
  "deposit": 8.9,
  "insuranceNumber": 1,
  "available": true,
  "model": "szympki",
  "office": null,
  "officeId": null,
  "seatNumber": 1,
  "technicalStatus": false,
  "fuelType": null,
  "photo": null,
  "type": null,
  "licence": null

}
```

### Delete car
###### Package into JSON
`delete api/car/id`
- returns status OK if office was successfully deleted

### Update car
###### Package into JSON
`put api/office`
- car sent in json must have id param.
 ```json
{
  "id": 12,
  "cost": 11.22,
  "model": "Accent"
}
```
- in reponse you get udpated car
 ```json
 {
  "id": 12,
  "cost": 11.22,
  "deposit": 0.0,
  "insuranceNumber": 0,
  "available": false,
  "model": "Accent",
  "office": {
    "id": 1,
    "location": "Nowy Sacz"
  },
  "officeId": null,
  "seatNumber": 0,
  "technicalStatus": false,
  "fuelType": null,
  "photo": null,
  "type": null,
  "licence": null
}
```
### Filter cars
`get api/car/service`
- returns list of all cars in service (unavailable)

`get api/car/functionalCar`
- returns list of all cars ready to rent

`get api/car/carByOffice/location`
- returns list of all cars in office given by location

`get api/car/carByOfficeId/id`
- returns list of all cars in office given by id

`get api/car/carByOfficeId/id/functional`
- returns only functional cars from office given by id

`get api/car/carByOffice/location/functional`
- returns only functional cars from location given by location

`get api/car/carByDate?params=1&params=16-12-2023&params=24-12-2023`
- filter by date, returns list of available cars in chosen location
  

## Photo
### Get all photos
get /api/photo
### Get photo by id
get /api/photo/{id}

### Add photo
post /api/photo
- Give the url

##### Request
```json
{
  "url":"imgSRC"
}
```
### Modify photo
put /api/photo

-Give both the url and the ID

##### Request
```json
{
    "id": 1,
    "url": "newSRC"
}
```
##### Response
```json
{
  "id": 1,
  "url": "newSRC"
}
```

### Delete photo by id
delete /api/photo/{id}

## Rental
### Get all rentals
get /api/rental

### Get all rentals by client's id
get /api/rental/client/{id}

### Get rental by id
get /api/rental/{id}

### Add rental
post /api/rental

To specify destination and origin offices use _destinationOfficeId_ and _originOfficeId_ fields respectively.
Fields _destinationOffice_ and _originOffice_ are **used only to return values**.
(the difference is _id_ at the end)
 
##### Request
```json
{
    "carId": 4,
    "clientId": 1,
    "originOfficeId": 8,
    "destinationOfficeId": 6,
    "protocolNumber": 420420420,
    "startDate": "2024-02-09",
    "endDate": "2024-02-11",
    "additions": {
        "ADDITION_DELIVERY": "pod dom",
        "ADDITION_INSURANCE": "OC w axa pan tez cwiczy",
        "ADDITION_DECORATION": "okleina"
    }
}
```

##### Response
```json
{
    "id": 2,
    "carId": 4,
    "clientId": 1,
    "originOffice": {
        "id": 8,
        "location": "Kingdom of Halemba"
    },
    "destinationOffice": {
        "id": 6,
        "location": "Chebzie :<"
    },
    "protocolNumber": 420420420,
    "startDate": "2024-02-09",
    "endDate": "2024-02-11",
    "additions": {
        "ADDITION_DELIVERY": "pod dom",
        "ADDITION_INSURANCE": "OC w axa pan tez cwiczy",
        "ADDITION_DECORATION": "okleina"
    },
    "cost": 950.4
}
```

### Modify rental
put /api/rental

To specify destination and origin offices use _destinationOfficeId_ and _originOfficeId_ fields respectively.
Fields _destinationOffice_ and _originOffice_ are **used only to return values**. (the difference is _id_ at the end)
Only specifed additons are preserved in database, so in order to add new addition you also have to specify old ones. To delete addition just don't specify it.

##### Request
```json
{
    "id": 1,
    "protocolNumber": 4,
    "originOfficeId": 6,
    "destinationOfficeId": 5,
    "additions": {
        "ADDITION_DECORATION": "okleina",
        "ADDITION_INSURANCE": "OC w axa pan tez cwiczy"
    }
}
```
##### Response
```json
{
    "id": 1,
    "carId": 5,
    "clientId": 1,
    "originOffice": {
        "id": 6,
        "location": "Chebzie :<"
    },
    "destinationOffice": {
        "id": 5,
        "location": "Bytom"
    },
    "protocolNumber": 4,
    "startDate": "2024-02-11",
    "endDate": "2024-02-11",
    "additions": [
        "ADDITION_DECORATION",
        "ADDITION_INSURANCE"
    ]
}
```

### Delete rental by id
delete /api/rental/{id}

### Statistics
get /api/statistics
###### Package into JSON
Variables:
- Total cars
- Cars that are rented
- Cars that are in service, services cars are marked as available
- Employees amount
- Offices amount
- Clients amount
- Earnings statistics array (24 float numbers, most recent last)
```json
{	
    "carCount": 1,
    "rentedCars": 1,
    "servicedCars": 0,
    "availableCars": 0,
    "clientCount": 1,
    "officeCount": 3,
    "employeeCount": 4,
    "income": [
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        1500.0,
        0.0,
        0.0,
        1500.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        4200.0
]
}
```
## Client
### Get client details
Get information about currently signed in Client
get /client/details
```json
{
    "id": 2,
    "username": "goodman",
    "email": "goodman@gmail.com",
    "name": "Jimmy",
    "lastName": "McGill",
    "companyInfo": null,
    "licenses": []
}
```
### Get all clients
get /client
```json
[
    {
        "id": 1,
        "username": "kielich420",
        "email": "kielich@gmail.com",
        "name": "Mateusz",
        "lastName": "Kieliszkowski",
        "companyInfo": {
            "name": "Budex Sp. z.o.o.",
            "nip": 123456789
        },
        "licenses": [
            {
                "id": 1,
                "licenseCategory": "B",
                "licenseNumber": 123458,
                "expirationDate": "2024-12-12"
            },
            {
                "id": 3,
                "licenseCategory": "A",
                "licenseNumber": 1312420,
                "expirationDate": "2030-01-01"
            }
        ]
    },
    {
        "id": 2,
        "username": "goodman",
        "email": "goodman@gmail.com",
        "name": "Jimmy",
        "lastName": "McGill",
        "companyInfo": null,
        "licenses": []
    }
]
```
### Get client by id
get /client/{id}

Response like above

### Delete client by id
delete /client/{id}

### Modify client
put /client

#### Request:
Client's id is required, chooses which client to modify.
To add new license do not specify its id, to modify existing license it is requierd to specify its id.
```json
{
    "id": 1,	// requierd - chooses client to modify
    "companyInfo": {
        "name": "Budex Sp. z.o.o.",
        "nip": 123456789
    },
    "licenses": [
        {
            "id": 1,
            "clientId": 1,
            "licenseCategory": "B",
            "licenseNumber": 123458,
            "expirationDate": "2024-12-12"
        },
        {
            "clientId": 1,
            "licenseCategory": "A",
            "licenseNumber": "1312420",
            "expirationDate": "2030-01-01"
        }
    ]
}
```

In response returns modified client (all fields).
