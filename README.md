## BlackJack-game-api

### This is api for well-known card game BlackJack.

### User (/users)
* GET /users returns a list of all users
#### Command: GET /users
server response example:
[
    {
        "id": 1,
        "name": "Dima",
        "balance": 13800,
        "drawBalance": 0
    },
    {
        "id": 2,
        "name": "Vasya",
        "balance": 0,
        "drawBalance": 0
    },
    {
        "id": 3,
        "name": "Petya",
        "balance": 0,
        "drawBalance": 0
    }
]

#### GET /users/{id} returns a user with id = {id}.
#### Command: GET /users/1
server response example:
{
    "id": 1,
    "name": "Dima",
    "balance": 13800,
    "drawBalance": 0
}


#### POST /users  adds a new user from request body and returns added user from db.
Command: POST /users
RequestBody example:
{
    "name" : "Alexey"
}

server response example:
{
    "id": 4,
    "name": "Alexey",
    "balance": 0,
    "drawBalance": 0
}

#### PUT /users/{id} changes the name of user with id = {id}. 
* New name is taken from requestBody.

Command: PUT /users/4
RequestBody example:
{
    "name" : "Alexey"
}

server response example:
{
    "id": 4,
    "name": "Alexey",
    "balance": 0,
    "drawBalance": 0
}


#### DELETE /users/{id} removes user with id = {id} from db. Returns 1 if successfull and 0 if not.

Command /users/4
server response example:
1


### Transaction</b> (/users/{userId}/transactions)

#### GET /users/{userId}/transactions returns all transactions of a user with id = {userId}

Command: /users/1/transactions

server response example:
[
    {
        "id": 1,
        "amount": 800,
        "currentBalance": 800,
        "date": 1502215132000,
        "transactionType": "REPLENISHMENT",
        "user": {
            "id": 1,
            "name": "Dima",
            "balance": 13800,
            "drawBalance": 0
        }
    },
    {
        "id": 5,
        "amount": 0,
        "currentBalance": 13800,
        "date": 1502216432000,
        "transactionType": "WINNINGS",
        "user": {
            "id": 1,
            "name": "Dima",
            "balance": 13800,
            "drawBalance": 0
        }
    }
]

#### GET /users/{userId}/transactions/{transactionId} returns transaction with id = {transactionId} of a user with id = {userId}. If user doesn't have this transaction, nothing is returned.

#### POST /users/{userId}/transactions/ replenishes user's balance with amount from requestBody.
Command: POST /users/1/transactions/
RequestBody example:
{
    "amount" : 1000
}

server response example:
{
    "id": 6,
    "amount": 1000,
    "currentBalance": 14800,
    "date": 1502226640557,
    "transactionType": "REPLENISHMENT",
    "user": {
        "id": 1,
        "name": "Dima",
        "balance": 14800,
        "drawBalance": 0
    }
}

#### DELETE /users/{userId}/transactions/{transactionId} removes transaction with id = {transactionId} of a user with id = {userId}. Returns 1 if successfull and 0 if not.


### Game (/users/{userId}/games)

#### GET /users/{userId}/games returns all games of a user with id = {userId}

Command: GET /users/1/games/

server response example:
[
    {
        "game": {
            "id": 4,
            "user": {
                "id": 1,
                "name": "Dima",
                "balance": 13800,
                "drawBalance": 1000
            },
            "bet": 0,
            "date": 1502216335000,
            "status": "PLAYER_WON"
        },
        "playerCards": [
            {
                "id": 42,
                "rank": "QUEEN",
                "suit": "DIAMONDS",
                "value": 10
            },
            {
                "id": 22,
                "rank": "SEVEN",
                "suit": "DIAMONDS",
                "value": 7
            }
        ],
        "dealerCards": [
            {
                "id": 10,
                "rank": "FOUR",
                "suit": "DIAMONDS",
                "value": 4
            },
            {
                "id": 25,
                "rank": "EIGHT",
                "suit": "HEARTS",
                "value": 8
            },
            {
                "id": 37,
                "rank": "JACK",
                "suit": "HEARTS",
                "value": 10
            }
        ],
        "dealerPoints": 22,
        "playerPoints": 17
    },
    {
        "game": {
            "id": 5,
            "user": {
                "id": 1,
                "name": "Dima",
                "balance": 13800,
                "drawBalance": 1000
            },
            "bet": 1000,
            "date": 1502226903000,
            "status": "IN_PROGRESS"
        },
        "playerCards": [
            {
                "id": 28,
                "rank": "NINE",
                "suit": "SPADES",
                "value": 9
            },
            {
                "id": 32,
                "rank": "TEN",
                "suit": "SPADES",
                "value": 10
            }
        ],
        "dealerCards": [
            {
                "id": 13,
                "rank": "FIVE",
                "suit": "HEARTS",
                "value": 5
            }
        ],
        "dealerPoints": 5,
        "playerPoints": 19
    }
]


#### GET /users/{userId}/games/active returns all active games of a user with id = {userId}

#### GET /users/{userId}/games/finished returns all finished games of a user with id = {userId}

#### GET /users/{userId}/games/{gameId} returns a game with id = {gameId} of a user with id = {userId}. If user doesn't have game with such id, nothing is returned.


#### POST /users/{userId}/games creates a new game, starts it and returns. Takes bet from requestBody.

Command: POST /users/1/games/

RequestBody example:
{
    "bet" : 1000
}

server response example: 
{
    "game": {
        "id": 7,
        "user": {
            "id": 1,
            "name": "Dima",
            "balance": 12800,
            "drawBalance": 2000
        },
        "bet": 1000,
        "date": 1502227293003,
        "status": "IN_PROGRESS"
    },
    "playerCards": [
        {
            "id": 47,
            "rank": "KING",
            "suit": "CLUBS",
            "value": 10
        },
        {
            "id": 38,
            "rank": "JACK",
            "suit": "DIAMONDS",
            "value": 10
        }
    ],
    "dealerCards": [
        {
            "id": 24,
            "rank": "EIGHT",
            "suit": "SPADES",
            "value": 8
        }
    ],
    "dealerPoints": 8,
    "playerPoints": 20
}



#### PUT /users/{userId}/games/{gameId}/stand  makes an action Stand for a player and returns an updated game.

Command: PUT /users/1/games/7/stand
{
    "game": {
        "id": 7,
        "user": {
            "id": 1,
            "name": "Dima",
            "balance": 13800,
            "drawBalance": 1000
        },
        "bet": 1000,
        "date": 1502227293000,
        "status": "DRAW"
    },
    "playerCards": [
        {
            "id": 47,
            "rank": "KING",
            "suit": "CLUBS",
            "value": 10
        },
        {
            "id": 38,
            "rank": "JACK",
            "suit": "DIAMONDS",
            "value": 10
        }
    ],
    "dealerCards": [
        {
            "id": 24,
            "rank": "EIGHT",
            "suit": "SPADES",
            "value": 8
        },
        {
            "id": 18,
            "rank": "SIX",
            "suit": "DIAMONDS",
            "value": 6
        },
        {
            "id": 1,
            "rank": "TWO",
            "suit": "HEARTS",
            "value": 2
        },
        {
            "id": 8,
            "rank": "FOUR",
            "suit": "SPADES",
            "value": 4
        }
    ],
    "dealerPoints": 20,
    "playerPoints": 20
}

#### PUT /users/{userId}/games/{gameId}/hit  makes an action Hit for a player and returns an updated game.
