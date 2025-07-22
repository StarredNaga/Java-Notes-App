![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)

# Java Notes App

My first console-based note management application built with Java.

## Key Features

- **Create notes**: Add new notes with title, content, and tags
- **Edit notes**: Modify existing note content
- **Delete notes**: Remove notes permanently
- **Search functionality**: Find notes by title, content, or tags
- **Sorting**: Order notes by ID, title, content, date, or tags
- **Automatic persistence**: Notes saved automatically to JSON file

## Used Libraries

**All libraries in libs folder as jar files**

| Library | Purpose | Version |
|---------|---------|---------|
| [text-io](https://github.com/beryx/text-io) | Console input validation and handling | 3.4.1 |
| [Gson](https://github.com/google/gson) | JSON serialization/deserialization | 2.13.1 |
| [jColor](https://github.com/dialex/JColor) | Console text coloring | 5.5.1 |

## Interface Screenshot

![App Screenshot](https://via.placeholder.com/468x300?text=Console+Interface+Example)

## Usage

After launching, interact with the application using the console menu:

1. Select an operation from the numbered menu

2. Follow prompts to enter note details or search parameters

3. Use the exit command to quit the application

### Available Operations

- Create new note
- Edit existing note
- Delete note
- Search notes (by title/content/tags)
- List notes (with sorting options)

## Data Structure

Notes are automatically persisted in app.json using this schema:

```json
[
  {
    "id": "8aa513b7-5f6b-43ff-871e-c0222bdc5917",
    "title": "Shopping List",
    "content": "Milk, Eggs, Bread",
    "tags": ["Some tag"],
    "creationDate" : "2025-07-07"
  }
]
```

## Field Descriptions

- **id**: Unique identifier (UUID) for each note
- **title**: Note header/title `(max 150 characters)`
- **content**: Main note text `(max 300 characters)`
- **tags**: Optional categorization labels `(max 15 tags)`
- **creationDate**: Automatic creation timestamp `(YYYY-MM-DD (ISO 8601))`
