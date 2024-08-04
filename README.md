# Notes on changes

## On Errors
You'll notice we have 600+ errors. Not to worry, most of them are caused by moving the files around and renaming the packages. What you need to do is change the package declaration and re-import the internal modules e.g. entities. You don't have to deliberately go into every file and change it. Just change whatever file you need.

## InputData

An `InputData` class should not contain any entity classes. Instead, pass in `gameId` so the interactor can use the `GameDataAccess` object to get `Game` from data storage. Also, add an empty constructor so that the input data can be automatically parsed from request body.

Before:

```java
public class PlaceLetterInputData {
  private final int x;
  private final int y;
  private final Character letter;
  private final Board board;
  private final Play play;
}
```

After:

```java
public class PlaceLetterInputData {
  private int gameId;
  private int x;
  private int y;
  private char letter;

  public PlaceLetterInputData() {}
}
```

## InputBoundary

Now the `execute` method should return something. At this point, it is best if all `execute` methods return a `Game` object so @alexlewis9 and @baleinegris won't have to worry about different response bodies.

Also, the method now throws `IOException, ClassNotFoundException` due to using the DAO to access `Game` objects.

## Interactors

Add `@Service` before the class declaration. This lets the framework know that this is a Singleton object and its lifecycle will be managed by the framework.

Add `@Autowired` before the constructor. This lets the framework perform dependency injection. The constructor should have only a `GameDataAccess` parameter.

Before:

```java
public class PlaceLetterInteractor implements PlaceLetterInputBoundary {

  private final PlaceLetterOutputBoundary presenter;

  public PlaceLetterInteractor(
    PlaceLetterOutputBoundary presenter
  ) {
    this.presenter = presenter;
  }

  @Override
  public void execute(PlaceLetterInputData data) {
    // Rest of the code
  }
}
```

After:

```java
@Service
public class PlaceLetterInteractor implements PlaceLetterInputBoundary {

  private final GameDataAccess gameDao;

  @Autowired
  public PlaceLetterInteractor(
    GameDataAccess gameDao
  ) {
    this.gameDao = gameDao;
  }

  @Override
  public Game execute(PlaceLetterInputData data) throws <List of Exceptions> {
    
    Game game = gameDao.get(data.getGameId());

    // Rest of the code
  }
}
```

Also, remember to `update` the game before the `execute` method returns.

## GameController

For each use case, add a new method to `GameController.java`.

Template:

```java
  @<RequestMethod>Mapping("/<Insert Usecase Name>/")
  public Game <usecase name>(@RequestBody <Usecase>InputData data) throws IOException, ClassNotFoundException {
    // Add a logging message that contains enough information to identify the game, the use case, and the input data,
    log.info(<Logging message>);
    return <Usecase>Interactor.execute(data);
  }
```

Also, add a private instance variable to `GameController` e.g. `PlaceLetterInputBoundary placeLetterInteractor` and initilize it in the constructor.

- `RequestMethod` should be `get` if you are only retrieving information, `post` or `put` if you are changing information.
