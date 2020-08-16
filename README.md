# MUSICCA

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Schema](#Schema)

## Overview
### Description
Allows users to contribute to a shared playlist by adding songs to be played, play and like the song and have them sorted based on the number of likes.

### App Evaluation
- **Category:** Social Networking / Music
- **Mobile:** This app would be primarily developed for mobile but would perhaps be just as viable on a computer, such as tinder or other similar apps. Functionality wouldn't be limited to mobile devices, however mobile version could potentially have more features.
- **Story:** Creates collaborative playlists for users to add, like/ unlike songs sorted based on the number of likes. Shows users other users' profiles including name, profile picture and music bio
- **Market:** Requires all users to have a spotify account so as to perform Spotify OAuth login and allow users to play the songs from Spotify after users add the songs to their playlist.
- **Habit:** This app could be used as often or unoften as the user wanted depending on how often users listen to music together with their friends.
- **Scope:** First I would start with the creation of the collaborative playlist, then perhaps it could incorporate a karaoke feature to allow users to sing along to the songs in the playlist. The app could evolve to be a platform that allows users with different music subscriptions such as apple music and amazon music.
## Product Spec
### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User logs in or signs up to access the app
* create playlist or join playlist with an invite code
* view playlist
* search songs
* like/ unlike a song
* App orders queue based on number of votes
* Profile pages for each user
* Timeline that allows users to view profiles for other users

**Optional Nice-to-have Stories**

* Karaoke to sing a long to the songs added in the playlist
* Join playlist with geofencing and QR code
* Next song in playlist starts automatically even with app in background
* Push notifications encourage users to use the app, add/like music

### 2. Screen Archetypes

* Login
* Sign Up
* Join Playlist Screen.
   * Allows user to join a playlist using an invitation code.
* Create Playlist Screen.
   * Allows user to create a playlist and invite other people.
* Profile Screen
   * Allows user to view and edit (separate activity) their profile photo and music bio
* Friends Screen
   * Allows user to view other app users' profiles including name, profile photo and music bio
* Song Database
   * Allows users to view available songs from the database that they can add to their playlist
* Current Playlist
   * Users can view sorted songs added in the playlist, add more songs, like/ unlike songs

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Join playlist
* Create playlist
* Profile
* Friends

**Flow Navigation** (Screen to Screen)
* Log-in -> Home (join playlist, create playlist, view profile, friends timeline)
* Sign-up -> new user creates an account -> Home
* Join Playlist -> enter invite code -> Current Playlist
* Create Playlist -> send invite -> Current Playlist
* Profile -> Image and Text field to be modified.
* Friends-> List of other users profiles

## Schema
### Models
* User
* Song
* Playlist
* Like

#### Song

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the song (default field) |
   | artist name   | String   | song artist |
   | song name     | String   | name of song |
   | spotifyID | String  | spotifyID used to play track |

#### Playlist

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the playlist (default field) |
   | author        | Pointer to User| playlist author |
   | name   | String   | playlist name |
   | photo   | Parsefile   | playlist photo icon |
   | songs         | Array list     | pointer to list of songs |

#### Like

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the like (default field) |
   | user   | Pointer to user   | user who liked the song|
   | song name     | Pointer to song   | song that was liked |
   | playlist name | Pointer to playlist  | playlist with the song that was liked |

### Networking
#### List of network requests by screen
   - Join Playlist Screen
      - (Read/GET) Query playlist

   - Create Playlist Screen
      - (Update/PUT) Create playlist

   - Profile Screen
      - (Read/GET) Query logged in user object
      - (Update/PUT) Update user profile image

   - Current Playlist Screen
      - (Read/GET) Query all songs in the playlist
      - (Update/PUT) Update songs in the playlist
#### Existing API Endpoints
##### Spotify SDK
- SpotifyAppRemote playerAPI
