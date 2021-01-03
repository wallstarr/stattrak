# NBA Player Statistics Application (Java 8)

## A handy desktop application for NBA fans that want to know how a player is performing!

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

# Demo Gifs
## Home Screen/Main Menu
![ezgif com-gif-maker](https://user-images.githubusercontent.com/51876078/103483339-ef478700-4e29-11eb-8d84-111d63026fdf.gif)
## Search Feature 
![ezgif com-gif-maker](https://user-images.githubusercontent.com/51876078/103485391-4012ac00-4e39-11eb-96cd-d821a365279b.gif)




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

### **Problems that I solved**

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



