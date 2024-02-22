Create an API where you can submit a purchase for a ticket. Details included in the receipt are:
a) From, To, User , price paid.
(i) User should include first and last name, email address
curl --location 'http://localhost:8080/reservation' \
--header 'Content-Type: application/json' \
--data-raw '{
  "from": "London",
  "to": "France",
  "user": {
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com"
  },
  "seat": {
    "seatNumber": 12,
    "section": "B"
  },
  "pricePaid": 100
}
'
response:
{
    "receiptId": "1d3f8e65-50bf-44b1-ad87-a1df77c3255e"
}


An API that shows the details of the receipt for the user
curl --location 'http://localhost:8080/receipt/3c0404c2-ba36-4557-a773-702156628e11'

response:
{
    "id": "1d3f8e65-50bf-44b1-ad87-a1df77c3255e",
    "userId": "1708585460730",
    "from": "London",
    "to": "France",
    "pricePaid": 100.0,
    "seatNumber": 12,
    "seatStatus": "RESERVED",
    "section": "B",
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com"
}

An API that lets you view the users and seat they are allocated by the requested section
curl --location 'http://localhost:8080/section/B'
response:
[
    {
        "seatNumber": 11,
        "seatStatus": "NOT_RESERVED",
        "section": "B"
    },
    {
        "seatNumber": 12,
        "seatStatus": "NOT_RESERVED",
        "section": "B"
    },
    {
        "seatNumber": 13,
        "seatStatus": "NOT_RESERVED",
        "section": "B"
    },
    {
        "seatNumber": 14,
        "seatStatus": "NOT_RESERVED",
        "section": "B"
    },
    {
        "seatNumber": 15,
        "seatStatus": "NOT_RESERVED",
        "section": "B"
    },
    {
        "seatNumber": 16,
        "seatStatus": "NOT_RESERVED",
        "section": "B"
    },
    {
        "seatNumber": 17,
        "seatStatus": "NOT_RESERVED",
        "section": "B"
    },
    {
        "seatNumber": 18,
        "seatStatus": "NOT_RESERVED",
        "section": "B"
    },
    {
        "seatNumber": 19,
        "seatStatus": "NOT_RESERVED",
        "section": "B"
    },
    {
        "seatNumber": 20,
        "seatStatus": "NOT_RESERVED",
        "section": "B"
    }
]


An API to remove a user from the train
curl --location --request DELETE 'http://localhost:8080/user/1708584821256'


An API to modify a user's seat
curl --location --request PUT 'http://localhost:8080/user/1708580192305' \
--header 'Content-Type: application/json' \
--data-raw '{
  "from": "London",
  "to": "France",
  "user": {
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com"
  },
  "seat": {
    "seatNumber": 12,
    "section": "B"
  },
  "pricePaid": 100
}
'
response:
{
    "receiptId": "1d3f8e65-50bf-44b1-ad87-a1df77c3255e"
}
