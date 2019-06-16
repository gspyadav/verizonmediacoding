# Processing YouTube videos Logs Files 

This is Verizon Media Interview Assignment Task and include 3 different solutions
1. Simple Linear Processing of given Log files.
2. Creating Separate Thread for each log file
3. Same as #2 but uses CompletableFuture 

## Running the tests

### Solution One Testing
Source Code: verizonmediacoding\src\main\java\com\verizon\media\SolutionOne\LogFileProcessorOne.java

1. Use the main() method for testing the functionality.

```
Change the file paths in LogFileProcessorOne.main() method example:
    String filePath1 = "C:\\tmp\\log1.txt";
    String filePath2 = "C:\\tmp\\log2.txt";
    String filePath3 = "C:\\tmp\\log3.txt";
Run the main() method.
```

### Solution Two Testing
Source Code: verizonmediacoding\src\main\java\com\verizon\media\SolutionOne\LogFileProcessorTwo.java

1. Use the main() method for testing the functionality. This classes uses the FilesMoviesDataStore class as a central DataBase, which is shared among all the Threads.
Synchronised the display() method on the Object moviesDb to make sure each time we print first 5 elements of each sorting criteria together for each Thread.

```
Create 3 file paths one for each seperate Thread
    String filePath1 = "C:\\tmp\\log1.txt";    --> Thead1
    String filePath2 = "C:\\tmp\\log2.txt";    --> Thead2
    String filePath3 = "C:\\tmp\\log3.txt";    --> Thead3
    
    Create a common data stor FilesMoviesDataStore Object and pass it to the Theard Object
            FilesMoviesDataStore moviesDb = new FilesMoviesDataStore();
            moviesDb.setMoviesListMap(new HashMap<String, List<Movie>>());
    
    Start all Threads --> thread1.start()

For Testing purpose I created the sample Test Steps in main(), just change the File Paths and run it.
```

### Solution Three Testing
Source Code: verizonmediacoding\src\main\java\com\verizon\media\SolutionOne\LogFileProcessorThree.java

1. Source code is almost similar to Solution Two solution with minor changes to show how CompletableFuture asynchronous calls work.

2. filePath is taken as a input to readFile() method and then use the return value of this method moviesList is used as a input to the next asynchronous call displayProcessor().

3. Using thenAcceptAsync because we are not expecting any return value.

4. Below is major logic where we calling readFile(filePath), and displayProcessor(moviesList) asynchronously.
```
    CompletableFuture<Void> cf1 = CompletableFuture.completedFuture(filePath).thenApplyAsync(filePath -> {
        System.out.println("Running Thread #: " + threadName);
        moviesDb.getMoviesListMap().put(filePath, new ArrayList<Movie>());
        return readFile(filePath);
    }).thenAcceptAsync(moviesList -> {
        displayProcessor(moviesList);
    });
```