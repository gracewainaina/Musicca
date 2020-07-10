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
- **Market:** Any individual with a Spotify account could choose to use this app.
- **Habit:** This app could be used as often or unoften as the user wanted depending on how often users listen to music together with their friends.
- **Scope:** First I would start with the creation of the collaborative playlist, then perhaps it could incorporate a karaoke feature to allow users to sing along to the songs in the playlist. The app could evolve to be a platform that allows users with different music subscriptions such as apple music and amazon music.
## Product Spec
### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User logs in or signs up to access the app
* User can create a new playlist and send invites to other users. (Think Spotify interface)
* User can join an existing playlist
* Profile pages for each user 
* User can see list of their friends (Instagram style)

**Optional Nice-to-have Stories**

* Karaoke to sing a long to the songs added in the playlist
* App suggestions of songs to add in the queue based on the genre/ mood of the songs being added
* Profile Add-On: Top music choices/ genres, etc.


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
* Friends
* Profile

Optional:
* Karaoke

**Flow Navigation** (Screen to Screen)
* Log-in -> Playlist
* Sign-up -> new user creates an account
* Playlists -> Join Playlist or Create Playlist -> Current Playlist
* Profile -> Image and Text field to be modified. 

## Schema 
### Models
* Songs
* Playlist

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
   
