# NBA Player Statistics Application

## A handy application for NBA fans that want to know how a player is performing!

Credit for the NBA data goes to Danny Park, creator of the balldontlie API. 

Link: https://www.balldontlie.io/#getting-started

*Features include*:
- Player stat retrieval and descriptions
- Player vs Player comparison
- Favorite players tab

Use this app to quickly find how your favorite players are performing and to find new players!
I wanted to design this because I'm a big fan of the NBA, so I thought it would be fun while also learning how to
design an app that has some practicality for someone like me, who wants to quickly search player statistics and compare
players in the middle of a game.  

### User Stories: ##
- As a user, I want to be able to find a player and look at his stats.
- As a user, I want to be able to compare the stats of two players
- As a user, I want to add/remove a player to/from my favorite players tab.
- As a user, I want to look at the players that I have favourited.
- As a user, I want the players I have favorited to be able to be saved to file. 
- As a user, I want to be able to load my favorite players from file when the program starts.

### **How to use my application**

- You can favorite a player by clicking on the "Favorite" button.
- You can unfavorite a player by clicking on the "Unfavorite" button.
- You can view a favorited player's stats by clicking on the player in the favorited players tab
- You can locate my visual component by searching for a player. There are logo + stadium background visuals depending 
on the team of the player. 
- You can save the state of my application by pressing the save button, which saves the 
favorited players tab.
- You can reload the state of my application by clicking the load players button. 

### **Phase 4: Task 2**

Option chosen: "Test and design a class that is robust.  You must have at least one method that throws a checked 
exception.  You must have one test for the case where the exception is expected and another where the exception is not
expected."

The class that is robust is the FavoritedPlayers class. 

The method involved is the addPlayer() method, which throws a PlayerAlreadyFavoritedException when a player
has already been favorited and is trying to be added. 

### **Phase 4: Task 3**

- The first problem I solved was in my SearchScreenController class. Before the changes, the "Favorite Players" tab
was implemented in the class. I thought that the SearchScreenController class should not be the class implementing 
that functionality because cohesion was low, so I created a FavoritedPlayersTab class, which lets that class do
all the work, thus increasing cohesion. Now, the SearchScreenController class has a field of type FavoritedPlayersTab.

- The second problem I solved was in both my SearchScreenController class and ComparisonScreenController class. Both
of these classes utilize an API that retrieves JSON data. Both of these classes should not
really be responsible for the implementation of using the API. So I created a StatGetter class in which the retrieval 
of JSON data is implemented in that class. In addition, both of these classes previously used the exact same methods for 
retrieving NBA data, so this not only improved cohesion but also reduced duplication. Now, both of these
Controller classes have a (field of type) StatGetter. 


