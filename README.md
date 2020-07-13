# MUSICCA

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Schema](#Schema)

## Overview
### Description
Allows users to contribute to a shared playlist by adding songs to be played and voting for them to determine which songs plays next in the playlist queue.

### App Evaluation
- **Category:** Social Networking / Music
- **Mobile:** This app would be primarily developed for mobile but would perhaps be just as viable on a computer, such as tinder or other similar apps. Functionality wouldn't be limited to mobile devices, however mobile version could potentially have more features.
- **Story:** Analyzes users music choices, and connects them to other users with similar choices. The user can then decide to message this person and befriend them if wanted. Once a playlist and users invited join the playlists, they can connect to their Spotify accounts to allow them to search for and add the songs in a playlist created. The users vote for songs to determine priority in the playlist.
- **Market:** Requires admin to have a spotify account so as to create a playlist and delete songs from a playlist, but the rest of the users can add songs to the queue and vote for them.
- **Habit:** This app could be used as often or unoften as the user wanted depending on how often users listen to music together with their friends.
- **Scope:** First I would start with the creation of the collaborative playlist, then perhaps it could incorporate a karaoke feature to allow users to sing along to the songs in the playlist. The app could evolve to be a platform that allows users with different music subscriptions such as apple music and amazon music.
## Product Spec
### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User logs in or signs up to access the app
* Admin can:
  * create/ delete playlist
  * add/ delete songs
  * view playlist
  * search songs
  * vote for song
* Other users can:
  * join playlist with code
  * view playlist
  * search songs
  * add song to playlist without logging in with Spotify
  * vote for song
* App orders queue based on number of votes
* Profile pages for each user 

**Optional Nice-to-have Stories**

* Karaoke to sing a long to the songs added in the playlist
* Join playlist with geofencing and QR code
* Profile Add-On: Top music choices/ genres, etc.
* Next song in playlist starts automatically even with app in background
* Push notifications encourage users to use the app, add/like music
* Users' playlists update live with a pub/sub model

### 2. Screen Archetypes

* Login 
* Register - User signs up or logs into their account
   * Upon Download/Reopening of the application, the user is prompted to log in to gain access to the app
* Profile Screen 
   * Allows user to upload a photo 
* Join Playlist Screen.
   * Allows user to join a playlist using an invitation code.
* Create Playlist Screen.
   * Allows user to create a playlist and invite other people.

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Playlist
* Profile

**Flow Navigation** (Screen to Screen)
* Log-in -> Home (join playlist, create playlist, view profile)
* Sign-up -> new user creates an account -> Home
* Join Playlist -> enter invite code -> Current Playlist
* Create Playlist -> send invite -> Current Playlist
* Profile -> Image and Text field to be modified. 

## Schema 
### Models
* User
* Song
* Playlist
* Vote

#### Songs

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the song (default field) |
   | artist name        | String| song artist |
   | lyrics        | String      | lyrics of the song |
   | description      | String   | playlist caption by author |
   | audio | ParseFile  | songtrack |
   | votesCount    | Number   | number of votes for the post |
   
#### Playlist

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the playlist (default field) |
   | author        | Pointer to User| playlist author |
   | songs         | Pointer     | pointer to list of songs |
   | description      | String   | playlist caption by author |

### Networking
#### List of network requests by screen
   - Playlist Screen
      - (Read/GET) Query all songs in the playlist
         ```swift
         let query = PFQuery(className:"Playlist")
         query.whereKey("author", equalTo: objectID)
         query.order(byDescending: "votesCount")
         query.findObjectsInBackground { (posts: [PFObject]?, error: Error?) in
            if let error = error { 
               print(error.localizedDescription)
            } else if let posts = posts {
               print("Successfully retrieved \(posts.count) posts.")
           // TODO: Do something with posts...
            }
         }
         ```
      - (Update/PUT) Update songs in the playlist
      - (Delete) Delete song in the playlist
   - Profile Screen
      - (Read/GET) Query logged in user object
      - (Update/PUT) Update user profile image
#### Existing API Endpoints
##### Spotify API
- Base URL - [https://api.spotify.com/v1](https://api.spotify.com/v1)

   HTTP Verb | Endpoint | Description
   ----------|----------|------------
    `GET`    | 	/v1/tracks/{id} | get a track
   
